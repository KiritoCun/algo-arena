package vn.udn.dut.algoarena.publicapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import vn.udn.dut.algoarena.publicapi.dto.Message;
import vn.udn.dut.algoarena.publicapi.request.GPTRequest;
import vn.udn.dut.algoarena.publicapi.response.GPTResponse;
import vn.udn.dut.algoarena.system.service.ISysConfigService;
import vn.udn.dut.algoarena.system.service.impl.SysConfigServiceImpl;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GPTService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GPTService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public String getGPTResponse(String userInput, String OPENAI_API_URL, String API_KEY) {
        int maxRetries = 3; // Maximum retry attempts
        int retryCount = 0;
        long backoffTime = 1000; // Initial backoff time in milliseconds

        while (retryCount < maxRetries) {
            try {
                // 1. Tạo thông điệp từ người dùng và thiết lập phản hồi chi tiết
                Message systemMessage = new Message("system", "You are a helpful assistant. Answer in detail, explaining each step clearly.");
                Message userMessage = new Message("user", userInput);

                GPTRequest gptRequest = new GPTRequest(
                        "gpt-3.5-turbo", // Model GPT-3.5
                        List.of(systemMessage, userMessage),
                        0.7 // Temperature
                );

                // 2. Tạo headers và request entity
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setBearerAuth(API_KEY);

                HttpEntity<GPTRequest> requestEntity = new HttpEntity<>(gptRequest, headers);

                // 3. Gửi yêu cầu POST đến API
                ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                        OPENAI_API_URL, requestEntity, String.class
                );

                // 4. Phân tích kết quả JSON
                GPTResponse gptResponse = objectMapper.readValue(
                        responseEntity.getBody(),
                        GPTResponse.class
                );

                // Trả về kết quả trả lời từ GPT, có thể thêm phần giải thích chi tiết
                String content = gptResponse.getChoices().get(0).getMessage().getContent();
                return content;

            } catch (HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                    // Xử lý lỗi 429: Too Many Requests
                    retryCount++;
                    if (retryCount >= maxRetries) {
                        return "API request quota exceeded, please try again later.";
                    }
                    try {
                        Thread.sleep(backoffTime); // Chờ trước khi thử lại
                        backoffTime *= 2; // Exponential backoff
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        return "Error while handling retry logic.";
                    }
                } else if (e.getStatusCode().is4xxClientError()) {
                    // Các lỗi 4xx khác
                    return "Client error: " + e.getMessage();
                } else if (e.getStatusCode().is5xxServerError()) {
                    // Các lỗi 5xx
                    return "Server error: " + e.getMessage();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Unexpected error occurred: " + e.getMessage();
            }
        }

        return "Failed to communicate with GPT-3.5 API after retries.";
    }
}
