package com.yangcb.seckill.web;

import java.util.Date;
import java.util.List;

import com.yangcb.seckill.model.Seckill;
import com.yangcb.seckill.service.SeckillFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yangcb.seckill.dto.Exposer;
import com.yangcb.seckill.dto.SeckillExecution;
import com.yangcb.seckill.dto.SeckillResult;
import com.yangcb.seckill.enums.SeckillStatEnum;
import com.yangcb.seckill.exception.RepeatKillException;
import com.yangcb.seckill.exception.SeckillCloseException;

@Controller
@RequestMapping("/seckill")
public class SeckillController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SeckillFacade seckillFacade;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		List<Seckill> list = seckillFacade.getSeckillList();
		model.addAttribute("list", list);
		return "list";
	}
	@RequestMapping(value = "/{seckillId}/detail")
	public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
		if (seckillId == null) {
			return "redirect:/seckill/list";
		}
		Seckill seckill = seckillFacade.getById(seckillId);

		if (seckill == null) {
			return "forward:/seckill/list";
		}
		model.addAttribute("seckill", seckill);
		return "detail";
	}

	@RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public SeckillResult<Exposer> exposer(@PathVariable("seckillId")Long seckillId) {
		SeckillResult<Exposer> result;
		try {
			Exposer exposer = seckillFacade.exportSeckillUrl(seckillId);
			result = new SeckillResult<Exposer>(true, exposer);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = new SeckillResult<Exposer>(false, e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/{seckillId}/{md5}/execution", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
			@CookieValue(value = "killPhone", required = false) Long phone, @PathVariable("md5") String md5) {

		if (phone == null) {
			return new SeckillResult<SeckillExecution>(false, "用户未注册");
		}
		try {
			//SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, phone, md5);
			SeckillExecution seckillExecution = seckillFacade.executeSeckillProcedure(seckillId, phone, md5);
			System.out.println(seckillExecution);
			return new SeckillResult<SeckillExecution>(true, seckillExecution);
		} catch (RepeatKillException e) {
			logger.error(e.getMessage(), e);
			SeckillExecution exception=new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
			return new SeckillResult<SeckillExecution>(true, exception);
		} catch (SeckillCloseException e) {
			logger.error(e.getMessage(), e);
			SeckillExecution exception=new SeckillExecution(seckillId, SeckillStatEnum.END);			
			return new SeckillResult<SeckillExecution>(true, exception);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new SeckillResult<SeckillExecution>(true, e.getMessage());
		}
	}

	@RequestMapping(value="/time/now",method=RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public SeckillResult<Long> time(){
		Date date=new Date();
		return new SeckillResult<Long>(true, date.getTime());
	}
	
	
	
	
}
