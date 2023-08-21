package kr.or.comeeat.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.comeeat.review.model.service.ReviewService;

@Controller
@RequestMapping(value="/review")
public class ReviewController {
	@Autowired
	private ReviewService reviewService;
	
	@GetMapping(value="/aroundPlace")
	public String aroudPlace() {
		return "review/aroundPlace";
	}
	
	@GetMapping(value="/detailRestaurant")
	public String detailRestaurant() {
		return "review/detailRestaurant";
	}
	
	
	
	
}