package Controller;

import Entity.Candidate;
import Entity.Vote;
import Reposiorty.CandidateRepository;
import Reposiorty.VoteRepository;
import ch.qos.logback.core.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private VoteRepository voteRepository;

    @GetMapping("/admin")
    public String showAdminPage(Model model) {
        List<Candidate> candidates = candidateRepository.findAll();
        model.addText("candidates");

        // Fetch vote count for each candidate
        Map<Candidate, List<Vote>> voteCountMap = new HashMap<>();
        for (Candidate candidate : candidates) {
            List<Vote> voteCount = voteRepository.findAll();
            voteCountMap.put(candidate, voteCount);
        }

        model.addText("voteCountMap");
        return "admin";
    }
}
