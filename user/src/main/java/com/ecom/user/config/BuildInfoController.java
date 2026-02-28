package com.ecom.user.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BuildInfoController {

//    @Value("${BUILD.ID:default}")
//    private String buildId;
//
//
//    @Value("${JAVA_HOME:default}")
//    private String buildName;

    public BuildInfo buildInfo;

//    http://localhost:8082/build-info
    @GetMapping("/build-info")
    public String getbuildInfo(){
        return "Build Id :"+buildInfo.getId()+"Build Id small  Build Nmae :"+buildInfo.getName();

    }
}


//build:
//id: 101
//version: v101
//name: "dev-build"
//type: