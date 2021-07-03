package xyz.torben.awc.json.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;
import xyz.torben.awc.json.records.MetarResponse;
import xyz.torben.awc.json.services.AviationWeatherService;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

@Controller
@RequestMapping("/metar")
public class MetarController {

  private final AviationWeatherService aviationWeatherService;

  @Autowired
  public MetarController(AviationWeatherService aviationWeatherService) {
    this.aviationWeatherService = aviationWeatherService;
  }

  @GetMapping
  @ResponseBody
  public ResponseEntity<MetarResponse> getCurrent(@RequestParam("q") String query) throws ParserConfigurationException, SAXException {
    try {
      return ResponseEntity.ok(this.aviationWeatherService.getForStation(query));
    } catch (URISyntaxException | IOException | InterruptedException | ParseException e) {
      return ResponseEntity.internalServerError().build();
    }
  }

}
