package engine;

public class AnswerResponse {
    private boolean success = false;
    private String feedback = "Wrong answer! Please, try again.";

    public AnswerResponse() {
    }

    public AnswerResponse(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}