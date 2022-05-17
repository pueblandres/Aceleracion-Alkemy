package com.alkemy.ong.domain.members;

import com.alkemy.ong.domain.utils.PageModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Value("${spring.application.pageSize}")
    int pageSize;

    private final MemberGateway memberGateway;

    public MemberService(MemberGateway memberGateway){
        this.memberGateway = memberGateway;
    }

    public Members save(Members members){
        return memberGateway.save(members);
    }

    public PageModel findByPage(int page) { return memberGateway.findByPage(page, pageSize);}

    public void delete(Long id){
        memberGateway.delete(id);
    }

    public Members update(Long id, Members members){ return memberGateway.update(id, members);}
}
