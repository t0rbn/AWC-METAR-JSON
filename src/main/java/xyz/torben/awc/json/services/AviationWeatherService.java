package xyz.torben.awc.json.services;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import xyz.torben.awc.json.records.MetarResponse;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

@Service
public class AviationWeatherService {

  public String getResponseString(String station) throws IOException, InterruptedException {
    var url = UriComponentsBuilder
            .fromUriString("https://www.aviationweather.gov/adds/dataserver_current/httpparam")
            .queryParam("dataSource", "metars")
            .queryParam("requestType", "retrieve")
            .queryParam("format", "xml")
            .queryParam("hoursBeforeNow", "1")
            .queryParam("mostRecent", "true")
            .queryParam("stationString", station)
            .build()
            .toUri();

    var request = HttpRequest.newBuilder().uri(url).build();
    var response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    return response.body();
  }

  private static MetarResponse xmlStringToResponse(String inputString) throws ParserConfigurationException, SAXException, IOException, ParseException {
    var builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    var document = builder.parse(new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8)));

    var responseNode = (Element) document.getDocumentElement();
    var dataNode = (Element) responseNode.getElementsByTagName("data").item(0);
    var metarNode = (Element) dataNode.getElementsByTagName("METAR").item(0);

    return new MetarResponse(
            metarNode.getElementsByTagName("raw_text").item(0).getTextContent(),
            Double.parseDouble(metarNode.getElementsByTagName("temp_c").item(0).getTextContent()),
            Double.parseDouble(metarNode.getElementsByTagName("dewpoint_c").item(0).getTextContent()),
            Double.parseDouble(metarNode.getElementsByTagName("wind_dir_degrees").item(0).getTextContent()),
            Double.parseDouble(metarNode.getElementsByTagName("wind_speed_kt").item(0).getTextContent()),
            Double.parseDouble(metarNode.getElementsByTagName("visibility_statute_mi").item(0).getTextContent()),
            Double.parseDouble(metarNode.getElementsByTagName("altim_in_hg").item(0).getTextContent()),
            ((Element) metarNode.getElementsByTagName("sky_condition").item(0)).getAttribute("sky_cover")
    );
  }

  public MetarResponse getForStation(String station) throws URISyntaxException, IOException, InterruptedException, ParserConfigurationException, SAXException, ParseException {
    return xmlStringToResponse(this.getResponseString(station));
  }

}



