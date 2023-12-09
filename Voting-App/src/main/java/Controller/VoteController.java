package Controller;

import Entity.Candidate;
import Entity.Vote;
import Reposiorty.CandidateRepository;
import Reposiorty.VoteRepository;
import ch.qos.logback.core.model.Model;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

public class VoteController {
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/voting")
    public String showVotingPage(Model model, Principal principal) {
        String username = principal.getName();
        User user = (User) userRepository.findByUsername(username).orElseThrow();

        // Check if the user has already voted
        if (voteRepository.existsByUserId(Long.valueOf(user.getName()))) {
            model.addText("message");
            return "voting-result";
        }

        // Fetch the list of candidates
        List<Candidate> candidates = candidateRepository.findAll();
        model.addText("candidates");
        model.addText("user");

        return "voting";
    }

    @PostMapping("/vote")
    public String vote(@RequestParam Long candidateId, Principal principal) {
        String username = principal.getName();
        User user = (User) userRepository.findByUsername(username).orElseThrow();

        // Check if the user has already voted
        if (voteRepository.existsByUserId(Long.valueOf(user.getName()))) {
            return "redirect:/voting?alreadyVoted";
        }

        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow();

        // Save the vote
        Vote vote = new Vote();
        vote.setCandidate(candidate);
        vote.setUser(user);
        voteRepository.save(vote);

        return "redirect:/voting?success";
    }

    @GetMapping("/voting-result")
    public String showVotingResult() {
        return "voting-result";
    }
}


