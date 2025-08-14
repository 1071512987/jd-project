package com.hzau.college.common.swagger;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;

//@EnableSwagger2
@EnableSwagger2WebMvc
@Configuration
public class SwaggerCfg implements InitializingBean {
    @Resource
    private Environment environment;
    private boolean enable;

//    @Bean
//    public Docket SearchDocket(){
//        return  groupDocket("01_(F)全局搜索相关接口",
//                "全局搜索模块文档",
//                "待定",
//                "1.0.0")
//                .select()
//                .paths(PathSelectors.regex("/(api/search/.*)"))
////                .paths(PathSelectors.ant("/api/university*/**"))
//                .build();
//    }

    @Bean
    public Docket FrontUserDocket(){
        return  groupDocket("01_(F)前台用户综合接口",
                "前台用户模块文档",
                "待定",
                "1.0.0")
                .select()
                .paths(PathSelectors.regex("/(api/frontUser.*)"))
                .build();
    }

    @Bean
    public Docket AdminUserDocket(){
        return  groupDocket("01_(B)后台用户综合接口",
                "后台用户模块文档",
                "待定",
                "1.0.0")
                .select()
                .paths(PathSelectors.regex("/(api/adminUser.*)"))
                .build();
    }

    @Bean
    public Docket FrontSysUserDocket(){
        return  groupDocket("02_(B)前台用户相关接口",
                "前台用户模块文档",
                "待定",
                "1.0.0")
                .select()
                .paths(PathSelectors.regex("/(api/frontSysUser.*)"))
                .build();
    }

    @Bean
    public Docket FrontSysRoleDocket(){
        return  groupDocket("02_(B)前台角色相关接口",
                "前台角色模块文档",
                "待定",
                "1.0.0")
                .select()
                .paths(PathSelectors.regex("/(api/frontSysRole.*)"))
                .build();
    }

    @Bean
    public Docket FrontSysResourceDocket(){
        return  groupDocket("02_(B)前台资源相关接口",
                "前台资源模块文档",
                "待定",
                "1.0.0")
                .select()
                .paths(PathSelectors.regex("/(api/frontSysResource.*)"))
                .build();
    }

    @Bean
    public Docket AdminSysUserDocket(){
        return  groupDocket("03_(B)后台用户相关接口",
                "后台用户模块文档",
                "待定",
                "1.0.0")
                .select()
                .paths(PathSelectors.regex("/(api/adminSysUser.*)"))
                .build();
    }

    @Bean
    public Docket AdminSysRoleDocket(){
        return  groupDocket("03_(B)后台角色相关接口",
                "后台角色模块文档",
                "待定",
                "1.0.0")
                .select()
                .paths(PathSelectors.regex("/(api/adminSysRole.*)"))
                .build();
    }

    @Bean
    public Docket AdminSysResourceDocket(){
        return  groupDocket("03_(B)后台资源相关接口",
                "后台资源模块文档",
                "待定",
                "1.0.0")
                .select()
                .paths(PathSelectors.regex("/(api/adminSysResource.*)"))
                .build();
    }

    @Bean
    public Docket BaseServiceDocket(){
        return  groupDocket("04_(F)全局基本业务相关接口",
                "全局基本业务模块文档",
                "待定",
                "1.0.0")
                .select()
                .paths(PathSelectors.regex("/(api/base.*)"))
                .build();
    }

    @Bean
    public Docket CollegeInfoDocket(){
        return  groupDocket("05_(BF)院校信息相关接口",
                "院校信息模块文档",
                "待定",
                "1.0.0")
                .select()
                .paths(PathSelectors.regex("/(api/college/.*)"))
//                .paths(PathSelectors.ant("/api/university*/**"))
                .build();
    }

    @Bean
    public Docket CollegeInfoListDocket(){
        return  groupDocket("05_(B)院校详细信息列表相关接口",
                "院校详细信息列表模块文档",
                "待定",
                "1.0.0")
                .select()
                .paths(PathSelectors.regex("/(api/collegeInfoList/.*)"))
//                .paths(PathSelectors.ant("/api/university*/**"))
                .build();
    }

    @Bean
    public Docket CollegeRankDocket(){
        return  groupDocket("05_(BF)院校排名相关接口",
                "院校排名模块文档",
                "待定",
                "1.0.0")
                .select()
                .paths(PathSelectors.regex("/(api/collegeRank/.*)"))
//                .paths(PathSelectors.ant("/api/university*/**"))
                .build();
    }

    @Bean
    public Docket SubjectDocket(){
        return  groupDocket("06_(BF)专业请求相关接口",
                "专业请求模块文档",
                "待定",
                "1.0.0")
                .select()
                .paths(PathSelectors.regex("/(api/subject.*)"))
//                .paths(PathSelectors.ant("/api/major*/**"))
                .build();
        }

    @Bean
    public Docket GuidanceDocket(){
        return  groupDocket("07_(F)志愿填报相关接口",
                "志愿填报模块文档",
                "待定",
                "1.0.0")
                .select()
                .paths(PathSelectors.regex("/(api/(college)?(subject)?Prefer.*)"))
                .build();
    }

    @Bean
    public Docket IntelligentDocket(){
        return  groupDocket("08_(F)智能填报相关接口",
                "智能填报模块文档",
                "待定",
                "1.0.0")
                .select()
                .paths(PathSelectors.regex("/(api/intelligent.*)"))
                .build();
    }

    @Bean
    public Docket PayDocket(){
        return  groupDocket("09_(FB)支付相关接口",
                "支付模块文档",
                "待定",
                "1.0.0")
                .select()
                .paths(PathSelectors.regex("/(api/pay.*)"))
                .build();
    }

    @Bean
    public Docket PostDocket(){
        return  groupDocket("10_(FB)文章相关接口",
                "文章模块文档",
                "待定",
                "1.0.0")
                .select()
                .paths(PathSelectors.regex("/(api/post.*)"))
                .build();
    }

    @Bean
    public Docket EvaluateDocket(){
        return  groupDocket("11_(BF)评测相关接口",
                "评测模块文档",
                "待定",
                "1.0.0")
                .select()
                .paths(PathSelectors.regex("/(api/evaluate.*)"))
                .build();
    }

//    @Bean
//    public Docket FrontDocket(){
//        return  groupDocket("(B)前台用户相关接口",
//                "前台用户模块文档",
//                "待定",
//                "1.0.0")
//                .select()
//                .paths(PathSelectors.regex("/(api/front.*)"))
//                .build();
//    }

        //Api是对文档的一些描述，有很多参数
    private ApiInfo apiInfo(String name, String description, String version){
        // 直接使用ApiInfo的构造方法，参数特别多，因此使用ApiInfoBuilder
        //链式编程
        return new ApiInfoBuilder()
                .title(name)
                .description(description)
                .version(version)
                .build();
    }

    //公共初始化代码
    private Docket basicDocket() {
         // Docket : 对接口文档的一些说明
        Parameter token = new ParameterBuilder()
//                .name(TokenFilter.TOKEN_FILTER)
                .name("Authorization")
                .description("用户登录令牌")
                .parameterType("header")
                .modelRef(new ModelRef("token"))
                .build();


        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(Collections.singletonList(token))
                .ignoredParameterTypes(
                        HttpSession.class,
                        HttpServletRequest.class,
                        HttpServletResponse.class)
                .enable(enable);
    }

    //初始化代码和通用参数
    private Docket groupDocket(String group, String name, String description, String version){
        return  basicDocket()
                .groupName(group)
                .apiInfo(apiInfo(name, description, version));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
         enable = environment.acceptsProfiles(Profiles.of("dev", "test"));
    }
}


