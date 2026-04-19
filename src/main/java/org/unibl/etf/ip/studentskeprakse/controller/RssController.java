package org.unibl.etf.ip.studentskeprakse.controller;


import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.ip.studentskeprakse.service.RssService;

@RestController
@RequestMapping("/api/rss")
public class RssController {

    @Autowired
    private RssService rssService;

    @GetMapping(value = "/prakse", produces = MediaType.APPLICATION_XML_VALUE)
    public String getInternshipRss() throws Exception {
        SyndFeed feed = rssService.createInternshipFeed();
        return new SyndFeedOutput().outputString(feed);
    }
}