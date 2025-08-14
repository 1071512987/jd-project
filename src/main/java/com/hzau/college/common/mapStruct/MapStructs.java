package com.hzau.college.common.mapStruct;

import com.hzau.college.pojo.dto.*;
import com.hzau.college.pojo.dto.guidance.AdmissionAndProjectDto;
import com.hzau.college.pojo.po.*;
import com.hzau.college.pojo.po.admin.AdminSysResource;
import com.hzau.college.pojo.po.admin.AdminSysRole;
import com.hzau.college.pojo.po.admin.AdminSysUser;
import com.hzau.college.pojo.po.college.*;
import com.hzau.college.pojo.po.evaluate.EvaluateBody;
import com.hzau.college.pojo.po.evaluate.EvaluateList;
import com.hzau.college.pojo.po.evaluate.EvaluateResult;
import com.hzau.college.pojo.po.front.FrontSysResource;
import com.hzau.college.pojo.po.front.FrontSysRole;
import com.hzau.college.pojo.po.front.FrontSysUser;
import com.hzau.college.pojo.po.front.FrontUserInfo;
import com.hzau.college.pojo.po.subject.JuniorSubjectInfo;
import com.hzau.college.pojo.po.subject.MajorSubjectInfo;
import com.hzau.college.pojo.po.subject.SubjectDetail;
import com.hzau.college.pojo.po.subject.SubjectJobRate;
import com.hzau.college.pojo.vo.LoginVo;
import com.hzau.college.pojo.vo.list.*;
import com.hzau.college.pojo.vo.list.college.*;
import com.hzau.college.pojo.vo.list.evaluate.EvaluateBodyVo;
import com.hzau.college.pojo.vo.list.subject.*;
import com.hzau.college.pojo.vo.list.sys.SysResourceVo;
import com.hzau.college.pojo.vo.list.sys.SysRoleVo;
import com.hzau.college.pojo.vo.list.sys.SysUserVo;
import com.hzau.college.pojo.vo.req.save.CollegeInfoListReqVo;
import com.hzau.college.pojo.vo.req.save.OrderReqVo;
import com.hzau.college.pojo.vo.req.save.PostReqVo;
import com.hzau.college.pojo.vo.req.save.UserCollegeListReqVo;
import com.hzau.college.pojo.vo.req.save.admin.AdminSysResourceReqVo;
import com.hzau.college.pojo.vo.req.save.admin.AdminSysRoleReqVo;
import com.hzau.college.pojo.vo.req.save.admin.AdminSysUserReqVo;
import com.hzau.college.pojo.vo.req.save.college.CollegeInfoReqVo;
import com.hzau.college.pojo.vo.req.save.college.CollegeMarkReqVo;
import com.hzau.college.pojo.vo.req.save.college.CollegeRankReqVo;
import com.hzau.college.pojo.vo.req.save.evaluate.EvaluateListReqVo;
import com.hzau.college.pojo.vo.req.save.evaluate.EvaluateResultReqVo;
import com.hzau.college.pojo.vo.req.save.front.FrontSysResourceReqVo;
import com.hzau.college.pojo.vo.req.save.front.FrontSysRoleReqVo;
import com.hzau.college.pojo.vo.req.save.front.FrontSysUserReqVo;
import com.hzau.college.pojo.vo.req.save.front.FrontUserAddInfoReqVo;
import com.hzau.college.pojo.vo.req.save.subject.JuniorSubjectInfoReqVo;
import com.hzau.college.pojo.vo.req.save.subject.MajorSubjectInfoReqVo;
import com.hzau.college.pojo.vo.req.save.subject.SubjectDetailReqVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 只需书写方法声明，标明转换的类型即可
 * ReqVo -> Po
 * Po -> vo
 */
@Mapper(uses = {
                MapStructFormatter.class
})
public interface MapStructs {
    //接口中变量都是 public static
    //为此接口生成代理对象（实现实例），通过此实例生成相应的转换方法
    MapStructs INSTANCE = Mappers.getMapper(MapStructs.class);

    /*
        po -> Vo
     */
    @Mapping(source = "loginTime",
            target = "loginTime",
            qualifiedByName = "Date2Millis")
    SysUserVo po2vo(FrontSysUser po);
    SysRoleVo po2vo(FrontSysRole po);
    JwtVo po2jwtVo(FrontSysUser po);
    SysResourceVo po2vo(FrontSysResource po);
    LoginVo po2LoginVo(FrontSysUser po);

    @Mapping(source = "loginTime",
            target = "loginTime",
            qualifiedByName = "Date2Millis")
    SysUserVo po2vo(AdminSysUser po);
    SysRoleVo po2vo(AdminSysRole po);
    JwtVo po2jwtVo(AdminSysUser po);
    SysResourceVo po2vo(AdminSysResource po);
    LoginVo po2LoginVo(AdminSysUser po);

    LoginVo dto2LoginVo(SysUserAndRolesDto dto);

    FrontUserInfoVo po2vo(FrontUserInfo po);

    EvaluateBodyVo po2vo(EvaluateBody po);

    PostVo po2vo(Post po);

    OrderVo po2vo(Order po);

    ScoreSectionVo po2vo(ScoreSection po);




//    UniversityInfoReqVo po2vo(CollegeInfo po);


    CollegeSearchVo po2vo(CollegeInfo po);
    CollegeNameVo po2nameVo(CollegeInfo po);
    CollegeCompVo po2compVo(CollegeInfo po);
    @Mapping( target = "collegeId", source = "id")
    @Mapping( target = "collegeName", source = "name")
    CollegeHotVo po2hotVo(CollegeInfo po);
    CollegeContactVo po2contactVo(CollegeInfo po);
    CollegeBaseVo po2baseVo(CollegeInfo po);
    CollegeVo po2collegeVo(CollegeInfo po);


    CollegeRankVo po2vo(CollegeRank po);
    CollegeMarkVo po2vo(CollegeMark po);
    CollegeInfoListVo po2vo(CollegeInfoList po);



    CollegeJobAreaVo po2vo(CollegeJobArea po);

    @Mapping( target = "subjectId", source = "thirdId")
    @Mapping( target = "subjectName", source = "thirdName")
    SubjectHotVo po2vo(MajorSubjectInfo po);
    @Mapping( target = "subjectId", source = "thirdId")
    @Mapping( target = "subjectName", source = "thirdName")
    SubjectHotVo po2vo(JuniorSubjectInfo po);
    SubjectDetailVo po2vo(SubjectDetail po);

    SubjectJobVo po2vo(SubjectJobRate po);
    @Mapping( target = "subjectId", source = "thirdId")
    @Mapping( target = "subjectName", source = "thirdName")
    SubjectBaseInfoVo po2baseVo(MajorSubjectInfo po);
    @Mapping( target = "subjectId", source = "thirdId")
    @Mapping( target = "subjectName", source = "thirdName")
    SubjectBaseInfoVo po2baseVo(JuniorSubjectInfo po);
    @Mapping( target = "subjectId", source = "thirdId")
    @Mapping( target = "subjectName", source = "thirdName")
    SubjectInfoVo po2infoVo(MajorSubjectInfo po);
    @Mapping( target = "subjectId", source = "thirdId")
    @Mapping( target = "subjectName", source = "thirdName")
    SubjectInfoVo po2infoVo(JuniorSubjectInfo po);





    @Mapping( target = "subjectId", source = "thirdId")
    @Mapping( target = "subjectName", source = "thirdName")
    ThirdSubjectVo po2thirdVo(MajorSubjectInfo po);
    @Mapping( target = "subjectId", source = "thirdId")
    @Mapping( target = "subjectName", source = "thirdName")
    ThirdSubjectVo po2thirdVo(JuniorSubjectInfo po);
    ThirdSubjectVo po2thirdVo(SubjectDetail po);



    /*
        reqVo -> po
     */

    FrontSysUser reqVo2po(FrontSysUserReqVo reqVo);
    FrontSysRole reqVo2po(FrontSysRoleReqVo reqVo);
    FrontSysResource reqVo2po(FrontSysResourceReqVo reqVo);
    FrontUserInfo addReqVo2po(FrontUserAddInfoReqVo reqVo);


    AdminSysUser reqVo2po(AdminSysUserReqVo reqVo);
    AdminSysRole reqVo2po(AdminSysRoleReqVo reqVo);
    AdminSysResource reqVo2po(AdminSysResourceReqVo reqVo);


    CollegeInfo reqVo2po(CollegeInfoReqVo reqVo);
    CollegeRank reqVo2po(CollegeRankReqVo reqVo);
    CollegeMark reqVo2po(CollegeMarkReqVo reqVo);
    CollegeInfoList reqVo2po(CollegeInfoListReqVo reqVo);

    MajorSubjectInfo reqVo2po(MajorSubjectInfoReqVo reqVo);
    JuniorSubjectInfo reqVo2po(JuniorSubjectInfoReqVo reqVo);
    SubjectDetail reqVo2po(SubjectDetailReqVo reqVo);
    UserCollegeList reqVo2po(UserCollegeListReqVo reqVo);

    EvaluateResult reqVo2po(EvaluateResultReqVo reqVo);
    EvaluateList reqVo2po(EvaluateListReqVo reqVo);

    Post reqVo2po(PostReqVo reqVo);

    Order reqVo2po(OrderReqVo reqVo);







    /*
         po -> dto
     */

    CollegeInfoDto po2dto(CollegeInfo po);
    AdmissionDto po2dto(Admission po);
    ProjectDto po2dto(Project po);
    CollegeConvertDto po2convertDto(Admission po);

    /*
        many -> one
        如一对一转换一样， 属性通过名字来自动匹配。因此， 名称和类型相同的不需要进行特殊处理
    private String SubjectCode;
    private String SubjectRemark;
    private Integer EnNationalCoop;
    private Integer EnEnterpriseCoop;
    private Integer EnLocalProject;
    private Integer EnTeacherKind;
    private String RequireCourseId;
    private String RequireCourseTypeId;
     */

    @Mapping( target = "collegeId", source = "convertDto.collegeId")
    @Mapping( target = "collegeName", source = "convertDto.collegeName")
    @Mapping( target = "collegeMajorGroupId", source = "convertDto.collegeMajorGroupId")
    CollegePreferSubjectVo many2oneCollege(CollegeConvertDto convertDto, AdmissionAndProjectDto aapDto);
    @Mapping( target = "collegeId", source = "convertDto.collegeId")
    @Mapping( target = "collegeName", source = "convertDto.collegeName")
    @Mapping( target = "collegeMajorGroupId", source = "convertDto.collegeMajorGroupId")
    SubjectPreferSubjectVo many2oneSubject(CollegeConvertDto convertDto, AdmissionAndProjectDto aapDto);



//    @Mapping(target = "idSuffix", source = "admissionDto.idSuffix")
//    SubjectPreferSubjectVo many2oneSubject(CollegeInfoDto infoDto, AdmissionDto admissionDto, ProjectDto projectDto);
}