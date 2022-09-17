package com.ssw.fssw.controller.coummuity;


import com.ssw.fssw.controller.comment.CommentForm;
import com.ssw.fssw.domain.Comment;
import com.ssw.fssw.domain.Community;
import com.ssw.fssw.repository.CommentApiRepository;
import com.ssw.fssw.repository.CommentRepository;
import com.ssw.fssw.repository.CommunityApiRepository;
import com.ssw.fssw.service.CommentService;
import com.ssw.fssw.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/community")
public class CommunityController {


    private final CommunityService communityService;
    private final CommunityApiRepository communityApiRepository;
    private final CommentService commentService;

    private final CommentApiRepository commentApiRepository;

    @GetMapping
    public String communityList(Long id, Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(required = false, defaultValue = "") String search, @RequestParam(required = false, defaultValue = "") String category) {
        Page<Community> communityList = communityApiRepository.findByTitleContainsAndContentsContainsAndCategoryContains(search, search, category,pageable);
        int startPage = Math.max(1, communityList.getPageable().getPageNumber() - 4); //현재 페이지 넘버를 가져온다.
        int endPage = Math.min(communityList.getTotalPages(), communityList.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("data", communityList);
        return "view/board/comList";
    }


    @GetMapping("/comWrite")
    public String comWrite(Model model) {
        model.addAttribute("comForm", new CommunityForm());
        return "view/board/comWrite";
    }


    @PostMapping("/comWrite")
    public String saveWrite(CommunityForm form) {

        Community community = new Community();
        community.setTitle(form.getTitle());
        community.setContents(form.getContent());
        community.setDate(form.getLocalDateTime().now());
        community.setCategory(form.getCategory());
        community.setNum(form.getBoard_num());

        communityService.saveCommunity(community);
        return "redirect:/community";
    }

    @GetMapping("/{id}/comDetail")
    public String pageDetail(@PathVariable("id") Long id, Model model) {
        //community 부분
        Community community = communityService.findOne(id);
        CommunityForm form = new CommunityForm();
        form.setTitle(community.getTitle());
        form.setContent(community.getContents());
        form.setLocalDateTime(community.getDate().now());

        model.addAttribute("community", community);

        // commentList 부분
        List<Comment> commentList = commentService.commentList(id);


        //댓글 작성부분
        CommentForm commentForm = new CommentForm();
        Long commentLastId = commentApiRepository.getCommentLastId();
        commentForm.setId(commentLastId.longValue());

        //대댓글 작성 부분
        commentForm.setOrder(commentApiRepository.getCommentLastOrder());
        model.addAttribute("comment", commentForm);
        model.addAttribute("comments", commentList);

        return "view/board/comDetail";
    }

    @GetMapping("/{id}/comModify")
    public String updateModifyForm(@PathVariable("id") Long id, Model model) {
        Community community = communityService.findOne(id);

        CommunityForm form = new CommunityForm();
        form.setTitle(community.getTitle());
        form.setContent(community.getContents());
        form.setCategory(community.getCategory());

        model.addAttribute("updateform", form);

        return "view/board/comModify";
    }

    @PostMapping("/{id}/comModify")
    public String updateModify(@PathVariable Long id, @ModelAttribute("updateform") CommunityForm form) {

        communityService.updateCommunity(id, form.getTitle(), form.getContent(), form.getCategory());

        return "redirect:/community/{id}/comDetail";
    }


    @GetMapping("/{id}/delete")
    public String deleteCommunity(@PathVariable("id") Long id) {
        communityApiRepository.deleteById(id);
        return "redirect:/community";
    }
}
