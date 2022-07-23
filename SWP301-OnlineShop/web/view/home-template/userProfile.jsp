<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${param.alterProfile != null}">
    <div class="fixed float-end t-55px" id="showAlter" style="    width: 26%;
         z-index: 1024;
         right: 36%;
         top: 6.6%;
         position: fixed;
         ">
        <div class="alert alert-success alert-dismissible fade in" id="alterfade" style="font-size: 15px; text-align: center">
            <a href="#" class="close" data-dismiss="alert" aria-label="close" style="
               transform: rotate(90deg);
               font-size: 34px;
               opacity: 0.2;
               top: 0px;">&times;</a>
            ${param.alterProfile} 
        </div>
    </div>
</c:if> 
<!-- Modal User Profile -->
<div class="modal fade" id="myModalProfilfe" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog w-30percent" role="document"">
        <div class="modal-content pd-20px">
            <div class="modal-header text-center" >
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"></button>
                <h4 class="modal-title fw-bolder-fz-20" id="myModalLabel">User Profile</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <form action="../user/profile" class="form-horizontal" method="post" id="submitFormEditProfile" enctype="multipart/form-data">
                        <c:set var="customer" value="${requestScope.user}"></c:set>

                            <div class="form-group">
                                <label for="avata" class="col-lg-2 col-sm-2 control-label"> </label>
                                <div class="col-md-8 text-center">
                                    <div id="closeBtn-attachedBoxImg-1" class="close-btn" onclick="closeImgProfile('attachedBoxImg-1', 'closeBtn-attachedBoxImg-1', 'attachedImg1');"></div>
                                <c:if test="${user.avatar == null}">
                                        <img id="attachedBoxImg-1" src="../assets/img/defaultUserAvatar.png" style="height:150px;" class="rounded-circle  image-thumbnail" />
                                </c:if>
                                <c:if test="${user.avatar != null}">
                                    <img id="attachedBoxImg-1" src="${user.avatar}" style="height:150px;" class="rounded-circle  image-thumbnail"/>
                                </c:if>
                                
                                <h6>Upload a different photo...</h6> 

                                <input type="file" name="attachedImg1" id="attachedImg1" accept="image/*" onchange="showImgProfile('attachedBoxImg-1', 'closeBtn-attachedBoxImg-1', 'attachedImg1')" 
                                       style="width: 50%; display: inline-block;"/>
                            </div>

                        </div>
                        <div class="form-group">
                            <div class="col-md-8">
                                <input type="hidden" class="form-control" name="id" value="${user.id}">
                            </div>
                        </div> 
                        <div class="form-group">
                            <label for="inputName" class="col-lg-3 col-sm-2 control-label">Name: </label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="fullname" value="${user.fullname}" pattern="^[a-z A-Z 0-9]+$" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail" class="col-lg-3 col-sm-2 control-label">Email: </label>
                            <div class="col-md-8">
                                <input type="text" readonly class="form-control" name="email" value="${user.email}">
                                <h6>You can't change email</h6> 
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputMobile"
                                   class="col-lg-3 col-sm-2 control-label">Mobile: </label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="mobile" value="${user.mobile}" pattern="^[0-9]+$"  required
                                       title="Your mobile must be number">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputAdress"
                                   class="col-lg-3 col-sm-2 control-label">Address: </label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="address" value="${user.address}" required="">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputGender" class="col-lg-3 col-sm-2 control-label">Gender:</label>
                            <div class="col-md-8">                                       
                                <label class="checkbox-inline">
                                    <input type="radio" name="gender" ${user.gender?"checked='checked'":"" }value="male" required=""> Male 
                                </label>
                                <label class="checkbox-inline">
                                    <input type="radio" name="gender" ${!user.gender?"checked='checked'":""}value="female" required=""> Female
                                </label>
                            </div>
                        </div>

                        <!--<input type="hidden" name="urlBeforreUpdate" value="${window.location.pathname}"/>-->
                        <div class="modal-footer">
                            <div class="col-md-offset">
                                <button type="submit" onclick="return confirm('Are you sure edit your profile?')" class="btn btn-info"> Save </button>

                                <button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    setTimeout(function () {
        const element = document.getElementById('showAlter');
        element.remove();
    }, 8000);
</script>
<script src="../../assets/js/user/userProfile.js" type="text/javascript"></script>