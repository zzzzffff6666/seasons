package com.zhang.seasons.service;

import com.zhang.seasons.bean.Laud;
import com.zhang.seasons.bean.Work;
import com.zhang.seasons.mapper.LaudMapper;
import com.zhang.seasons.mapper.WorkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class WorkService {
    private static final String[] SORT_SEQ = new String[] {"created", "price", "laud_num"};
    private static final String[] SORT_TYPE = new String[] {" asc", " desc"};

    @Autowired
    private WorkMapper workMapper;
    @Autowired
    private LaudMapper laudMapper;

    // Work 部分

    public boolean insertWork(Work work) {
        return workMapper.insert(work) == 1;
    }

    public boolean deleteWork(int wid) {
        return workMapper.delete(wid) == 1;
    }

    public boolean updateWorkInfo(Work work) {
        return workMapper.update(work) == 1;
    }

    public boolean updateWorkLaud(int wid, int addition) {
        return workMapper.updateLaud(wid, addition) == 1;
    }

    public boolean updateWorkState(int wid, int state) {
        return workMapper.updateState(wid, state) == 1;
    }

    public int selectWorkCreator(int wid) {
        return workMapper.selectCreator(wid);
    }

    public Work selectWork(int wid) {
        return workMapper.select(wid);
    }

    public List<Work> selectWorkByTitle(String title, int[] sort) {
        return workMapper.selectByTitle(title, SORT_SEQ[sort[0]] + SORT_TYPE[sort[1]]);
    }

    public List<Work> selectWorkByStyle(String style, int[] sort) {
        return workMapper.selectByStyle(style, SORT_SEQ[sort[0]] + SORT_TYPE[sort[1]]);
    }

    public List<Work> selectWorkByCreator(int creator, int[] sort) {
        return workMapper.selectByCreator(creator, SORT_SEQ[sort[0]] + SORT_TYPE[sort[1]]);
    }

    public List<Work> selectAllWork(int[] sort) {
        return workMapper.selectAll(SORT_SEQ[sort[0]] + SORT_TYPE[sort[1]]);
    }

    public List<Work> selectDisapproveWork(int[] sort) {
        return workMapper.selectDisapprove(SORT_SEQ[sort[0]] + SORT_TYPE[sort[1]]);
    }

    public List<Work> selectAllWorkAsAdmin(int[] sort) {
        return workMapper.selectAllAsAdmin(SORT_SEQ[sort[0]] + SORT_TYPE[sort[1]]);
    }

    public List<Work> selectWorkByList(List<Integer> list) {
        return workMapper.selectByList(list);
    }

    // Laud 部分

    @Transactional
    public boolean insertLaud(Laud laud) {
        return laudMapper.insert(laud) == 1 && workMapper.updateLaud(laud.getWid(), 1) == 1;
    }

    @Transactional
    public boolean deleteLaud(int uid, int wid) {
        return laudMapper.delete(uid, wid) == 1 && workMapper.updateLaud(wid, -1) == 1;
    }

    public boolean isLaud(int uid, int wid) {
        return laudMapper.select(uid, wid) == 1;
    }

    public int selectLaudNum(int wid) {
        return laudMapper.selectCount(wid);
    }

    public List<Laud> selectLaudList(int uid) {
        return laudMapper.selectList(uid);
    }
}
