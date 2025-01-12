package vn.udn.dut.algoarena.publicapi.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import vn.udn.dut.algoarena.publicapi.dto.Message;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true) // Bỏ qua các trường không cần thiết
public class GPTResponse {

    private List<Choice> choices;

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Choice {
        private Message message;

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }
    }
}
