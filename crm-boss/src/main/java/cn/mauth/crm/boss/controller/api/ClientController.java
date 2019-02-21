package cn.mauth.crm.boss.controller.api;

import cn.mauth.crm.common.domain.BusinessOpportunity;
import cn.mauth.crm.common.domain.Client;
import cn.mauth.crm.common.service.ClientService;
import cn.mauth.crm.util.base.BaseController;
import cn.mauth.crm.util.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/crm/v1/clients")
@Api("客户API")
public class ClientController extends BaseController{

    @Autowired
    private ClientService service;

    @GetMapping("{id}")
    @ApiOperation("根据id查询客户")
    public Result findById(@PathVariable Long id){

        Client client=service.findById(id);

        if(client==null)
            return error("没有找到id:"+id+"的客户");

        return ok(client);
    }

    @GetMapping
    @ApiOperation("查询所有客户")
    public Result findAll(){

        List<Client> list=service.findAll();

        if(list==null || list.size()==0)
            return error("还没有客户");

        return ok(list);
    }

    @GetMapping("/page")
    @ApiOperation("分页查询客户")
    public Result page(Pageable pageable){

        return ok(service.page(pageable));
    }

}
