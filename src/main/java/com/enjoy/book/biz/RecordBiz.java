package com.enjoy.book.biz;

import com.enjoy.book.bean.Book;
import com.enjoy.book.bean.Member;
import com.enjoy.book.bean.Record;
import com.enjoy.book.dao.BookDao;
import com.enjoy.book.dao.MemberDao;
import com.enjoy.book.dao.RecordDao;
import com.enjoy.book.util.DBHelper;
import com.enjoy.book.util.DateHelper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public class RecordBiz {
    RecordDao  recordDao = new RecordDao();
    BookDao  bookDao = new BookDao();
    MemberDao memberDao = new MemberDao();
    MemberBiz  memberBiz = new MemberBiz();
    public List<Record> getRecordsByIdNum(String idNum){
        List<Record> records = null;
        try {
            records=recordDao.getRecordsByIdNum(idNum);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return records;
    }
    public List<Record> getRecordsByMemberId(long memberId){
        List<Record> records = null;
        try {
            records=recordDao.getRecordsByMemberId(memberId);

            Member member = memberBiz.getById(memberId);
            for(Record record:records){
                long bookId = record.getBookId();
                Book book = bookDao.getById(bookId);
                record.setBook(book);
                record.setMember(member);

                long day = member.getType().getKeepDay();

                java.sql.Date  rentDate = record.getRentDate();

                java.sql.Date backDate = DateHelper.getNewDate(rentDate,day);
                record.setBackDate(backDate);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return records;
    }


    public int add(long memberId,List<Long> bookIdList,long userId){
        try {

            DBHelper.beginTransaction();
            double total = 0;

            for(long bookId: bookIdList){

                Book book = bookDao.getById(bookId);

                double price = book.getPrice();
                double regPrice = price*0.3f;
                total += regPrice;

                recordDao.add(memberId,bookId,regPrice,userId);

                bookDao.modify(bookId,-1);

            }

            memberDao.modifyBalance(memberId,0-total);

            DBHelper.commitTransaction();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                DBHelper.rollbackTransaction();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0;
        }

        return 1;

    }

    public int  modify(long memberId, List<Long> recordIds, long userId) {
         try {
            DBHelper.beginTransaction();
            double total = 0;
            Member member = memberBiz.getById(memberId);
            for(long recordId:recordIds){

               Record record = recordDao.getById(recordId);

                java.sql.Date  backDate = DateHelper.getNewDate(record.getRentDate(),member.getType().getKeepDay());

                java.util.Date currentDate = new java.util.Date();
                int day = 0;
                if(currentDate.after(backDate)){

                     day = DateHelper.getSpan(currentDate,backDate);
                }
               total += record.getDeposit()-day;

                recordDao.modify(day,userId,recordId);

                bookDao.modify(record.getBookId(),1);
            }

            memberDao.modifyBalance(memberId,total);
            DBHelper.commitTransaction();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                DBHelper.rollbackTransaction();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0;
        }

        return 1;

    }
    public  int modify(long id){
        int count =0;
        try {
            count = recordDao.modify(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;


    }


    public List<Map<String,Object>> query(int typeId, String keyword){
        List<Map<String,Object>> rows = null;
        try {
            rows = recordDao.query(typeId,keyword);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rows;

    }
}
