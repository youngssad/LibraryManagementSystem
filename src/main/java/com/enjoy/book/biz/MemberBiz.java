package com.enjoy.book.biz;

import com.enjoy.book.bean.Member;
import com.enjoy.book.bean.MemberType;
import com.enjoy.book.dao.MemberDao;
import com.enjoy.book.dao.MemberTypeDao;

import java.sql.SQLException;
import java.util.List;

public class MemberBiz {
  MemberDao  memberDao = new MemberDao();
  MemberTypeDao   typeDao = new MemberTypeDao();
  public int add(String name,String pwd,long typeId,double balance,String tel,String idNumber){
    int count = 0;
    try {
      count = memberDao.add(name,pwd,typeId,balance,tel,idNumber);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return count;
  }
  public int modify(long id,String name,String pwd,long typeId,double balance,String tel,String idNumber){
    int count = 0;
    try {
      count = memberDao.modify(id,name,pwd,typeId,balance,tel,idNumber);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return count;
  }
  public int remove(long id) throws Exception {

    Member member = getById(id);
    if(member.getBalance()>0){
      throw new Exception("This member's account has balance, delete fail");
    }

    if(memberDao.exits(id)){
      throw new Exception("This member has foreign key, delete fail");
    }

    int count =0;
    try {
      count = memberDao.remove(id);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return count;

  }
  public int modifyBalance(String idNumber,double amount){
    int count = 0;
    try {
      count = memberDao.modifyBalance(idNumber,amount);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return count;
  }
  public List<Member> getAll(){
    MemberTypeDao typeDao = new MemberTypeDao();
    List<Member> members = null;
    try {
      members =  memberDao.getAll();
      for(Member member:members){

        MemberType type = typeDao.getById(member.getTypeId());
        member.setType(type);
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();

    }
    return members;

  }
  public Member getById(long id){
    Member member = null;
    try {
      member = memberDao.getById(id);
      MemberType memberType = typeDao.getById(member.getTypeId());
      member.setType(memberType);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return member;
  }


  public Member getByIdNumber(String idNumber){
    Member member = null;
    try {
      member = memberDao.getByIdNumber(idNumber);
      MemberType memberType = typeDao.getById(member.getTypeId());
      member.setType(memberType);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return member;
  }



}
