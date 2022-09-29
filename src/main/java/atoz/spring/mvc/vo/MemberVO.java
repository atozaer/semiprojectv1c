package atoz.spring.mvc.vo;

import org.springframework.stereotype.Component;

@Component
public class MemberVO {
    private String mno;
    private String userid;
    private String passwd;
    private String name;
    private String email;
    private String regdate;

    public MemberVO() {
    }

    public String getMno() {
        return mno;
    }

    public void setMno(String mno) {
        this.mno = mno;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    @Override
    public String toString() {
        return "MemberVO{" +
                "mno='" + mno + '\'' +
                ", userid='" + userid + '\'' +
                ", passwd='" + passwd + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", regdate='" + regdate + '\'' +
                '}';
    }


}
