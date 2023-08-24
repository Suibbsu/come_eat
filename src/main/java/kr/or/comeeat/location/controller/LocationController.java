package kr.or.comeeat.location.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.comeeat.location.model.model.sevice.LocationService;
import kr.or.comeeat.location.model.vo.LocationData;

@Controller
@RequestMapping(value="/location")
public class LocationController {
	@Autowired
	private LocationService locationService;
	
	
	//검색
	@PostMapping(value="/search")
	public String searchList(String pageNo,String search,Model model) {
		LocationData locationData = locationService.searchList(pageNo,search);
		model.addAttribute("list", locationData.getList());
		model.addAttribute("navi", locationData.getNavi());
		model.addAttribute("title", locationData.getTitle());
		return "search/searchList";
	}
	
	//지역별맛집 페이지로 이동
	@GetMapping(value="/list")
	public String locationList() {
		return "location/location";
	}
	
	@GetMapping(value="/aroundPlace")
	public String aroudPlace() {
		return "location/aroundPlace";
	}
	
	//전체맛집
	@GetMapping(value="/foodList")
	public String busan(String pageNo,String lo, Model model) {

		LocationData locationData = locationService.foodList(pageNo,lo);
		model.addAttribute("list", locationData.getList());
		model.addAttribute("navi", locationData.getNavi());
		model.addAttribute("title", locationData.getTitle());
		return "location/location";
	}
	
	//지도전체출력
	@ResponseBody
	@GetMapping(value="/map")
	public List locationMap(String loCode) {
		List list = locationService.locationMap(loCode);
		return list;
	}
	
	@GetMapping(value="/searchAroundPlace")
	public String searchAroundPlace(String searchPlace, Model model) {
		List list = locationService.searchAroundPlace(searchPlace);
		model.addAttribute("searchList", list);
		return "location/aroundPlace";
	}
}
