package cn.mauth.crm.common.service;

import cn.mauth.crm.common.domain.SysLoginLog;
import cn.mauth.crm.common.repository.SysLoginLogRepository;
import cn.mauth.crm.util.common.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysLoginLogSerVice {

    @Autowired
    private SysLoginLogRepository repository;

    @Autowired
    private RedisService redisService;

    private final static Logger log= LoggerFactory.getLogger(SysLoginLog.class);

    public boolean add(SysLoginLog sysLoginLog){
        boolean flag=false;
        try {
            repository.save(sysLoginLog);
            flag=true;
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return flag;
    }

    public SysLoginLog findById(Long id){
        return repository.findById(id).get();
    }

    public Page<SysLoginLog> page(Pageable pageable){

        return repository.findAll((root, query, cb) -> {
            List<Predicate> list=new ArrayList<>();

            if(!redisService.isAdmin()){
                list.add(cb.equal(root.get("userId"),redisService.getUserId()));
            }

            return cb.and(list.toArray(new Predicate[list.size()]));
        }, PageUtil.getPageable(pageable));
    }

    public List<SysLoginLog> findAll(){
        return repository.findAll((root, query, cb) -> {
            if(!redisService.isAdmin()){
                return cb.equal(root.get("userId"),redisService.getUserId());
            }
            return null;
        });
    }
}
