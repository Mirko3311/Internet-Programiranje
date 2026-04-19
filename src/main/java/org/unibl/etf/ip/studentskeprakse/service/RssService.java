package org.unibl.etf.ip.studentskeprakse.service;


import com.rometools.rome.feed.synd.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.studentskeprakse.entity.Internship;
import org.unibl.etf.ip.studentskeprakse.repository.InternshipRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RssService {

    @Autowired
    private InternshipRepository internshipRepository;

    public SyndFeed createInternshipFeed() {
        SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType("rss_2.0");
        feed.setTitle("Studentske Prakse - Portal");
        feed.setDescription("Najnovije objavljene prakse za studente");
        feed.setLink("http://localhost:4200/dashboard/prakse");

        List<Internship> internships = internshipRepository.findAll();
        List<SyndEntry> entries = new ArrayList<>();

        for (Internship internship : internships) {
            SyndEntry entry = new SyndEntryImpl();
            entry.setTitle(internship.getTitle());
            entry.setLink("http://localhost:4200/dashboard/prakse/" + internship.getId());
            entry.setPublishedDate(new Date());

            SyndContent description = new SyndContentImpl();
            description.setType("text/plain");
            description.setValue(internship.getDescription());
            entry.setDescription(description);

            entries.add(entry);
        }

        feed.setEntries(entries);
        return feed;
    }
}