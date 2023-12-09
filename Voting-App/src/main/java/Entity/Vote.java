package Entity;

import jakarta.persistence.*;
import org.apache.catalina.User;

public class Vote {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void setCandidate(Candidate candidate) {
    }

    public void setUser(User user) {

    }
}


