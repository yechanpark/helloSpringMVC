package kr.ac.hansung.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.ac.hansung.model.Offer;
import kr.ac.hansung.service.OffersService;

@Controller
public class OffersController {

	private OffersService offersService;

	@Autowired // OffersService Ÿ���� Bean ����
	public void setOffersService(OffersService offersService) {
		this.offersService = offersService;
	}

	@RequestMapping("/offers") // �� url�� ��û�� �Ǹ�
	public String showOffers(Model model) { // �� �Լ��� �Ҹ��鼭
		List<Offer> offers = offersService.getCurrent();

		model.addAttribute("offers", offers);
		return "offers"; // �� jsp������ �Ҹ���.
	}

	@RequestMapping("/createoffer")
	public String createOffer(Model model) {
		model.addAttribute(new Offer());
		return "createoffer";
	}

	@RequestMapping("/docreate")
	public String doCreate(Model model, @Valid Offer offer, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println("Form data does not validate");
			List<ObjectError> errors = result.getAllErrors();
			for (ObjectError error : errors) {
				System.out.println(error.getDefaultMessage());
			}
			// ���� �߻� �� ���� Form �Է� ��ġ�� �̵�
			return "createoffer";
		}
		offersService.insert(offer);
		return "offercreated";
	}
}
