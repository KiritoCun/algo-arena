package vn.udn.dut.algoarena.publicapi.response;

import vn.udn.dut.algoarena.publicapi.dto.Message;

import java.util.List;

public class GPTResponse {

    private List<Choice> choices;

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

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
