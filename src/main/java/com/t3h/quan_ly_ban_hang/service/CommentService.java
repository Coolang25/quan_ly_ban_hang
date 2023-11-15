package com.t3h.quan_ly_ban_hang.service;

import com.t3h.quan_ly_ban_hang.dto.BillDto;
import com.t3h.quan_ly_ban_hang.dto.CommentDto;
import com.t3h.quan_ly_ban_hang.dto.OrderedItemDto;
import com.t3h.quan_ly_ban_hang.entities.Bill;
import com.t3h.quan_ly_ban_hang.entities.Comment;
import com.t3h.quan_ly_ban_hang.entities.OrderedItem;
import com.t3h.quan_ly_ban_hang.entities.ProductSize;
import com.t3h.quan_ly_ban_hang.repository.CommentRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentService {
    @Autowired
    CommentRepo commentRepo;

    public CommentDto create(CommentDto commentDto) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDto, comment);

        commentRepo.save(comment);
        commentDto.setId(comment.getId());

        return commentDto;
    }
}
