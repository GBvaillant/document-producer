package com.example.document_producer.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    private final S3Client s3Client;
    private final KafkaService kafkaService;

//    @Value("${aws.s3.bucketName}")
    private final String bucketName = "document-processor";

    public DocumentService(S3Client s3Client, KafkaService kafkaService) {
        this.s3Client = s3Client;
        this.kafkaService = kafkaService;
    }

    public List<String> getAllDocumentKeys() {
        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();

        ListObjectsV2Response result = s3Client.listObjectsV2(request);
        return result.contents().stream()
                .map(S3Object::key)
                .collect(Collectors.toList());
    }

    public ResponseInputStream<GetObjectResponse> getFileFromS3(String key) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        return s3Client.getObject(request);
    }

    public void searchDocumentFromKey(String keyword) {
        List<String> documentKeys = getAllDocumentKeys();
        String topic = "document-filter";

        for (String key : documentKeys) {
            try (InputStream input = getFileFromS3(key);
                 PDDocument document = PDDocument.load(input)) {

                PDFTextStripper stripper = new PDFTextStripper();
                String content = stripper.getText(document);

                if (content != null && content.toLowerCase().contains(keyword.toLowerCase())) {
                    String message = String.format("Arquivo %s contém a palavra: %s", key, keyword);
                    kafkaService.sendMessage(topic, message);
                    System.out.println(message);
                } else {
                    System.out.println("Arquivo " + key + " não contém a palavra: " + keyword);
                }

            } catch (Exception e) {
                System.err.println("Erro ao processar o arquivo " + key + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }



}
