<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../include/header.jsp" %>
<!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1 class="m-0 text-dark">DashBoard Management</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Home</a></li>
              <li class="breadcrumb-item active">Starter Page</li>
            </ol>
          </div><!-- /.col -->
        </div><!-- /.row -->
      </div><!-- /.container-fluid -->
      
</div>
        <div class="col-12">
            <div class="card">
              <div class="card-header">
                <h3 class="card-title">게시판 생성 리스트</h3>

                <div class="card-tools">
                  <div class="input-group input-group-sm" style="width: 150px;">
                    <input type="text" name="table_search" class="form-control float-right" placeholder="Search">

                    <div class="input-group-append">
                      <button type="submit" class="btn btn-default"><i class="fas fa-search"></i></button>
                    </div>
                  </div>
                </div>
              </div>
              <!-- /.card-header -->
              <div class="card-body table-responsive p-0">
              
              
							<form id="form_bodtype" role="form" action="/admin/bodtype/update" method="post">
								<div class="row">
									
									<div class="col-sm-12">
										<!-- text input -->
										<div class="form-group">
											<label>보드타입</label> 
											${bodTypeVO.bod_type}
											<input value="${bodTypeVO.bod_type}" name="bod_type" type="hidden" class="form-control"
												placeholder="게시판 타입을 입력해주세요.">
										</div>
									</div>
									<div class="col-sm-12">
										<!-- text input -->
										<div class="form-group">
											<label>보드네임</label> 												
											<input value="${bodTypeVO.bod_name}" name="bod_name" type="text" class="form-control"												
											placeholder="게시판 이름을 입력해 주세요.">
										</div>
									</div>
									<div class="col-sm-12">
										<!-- text input -->
										<div class="form-group">
											<label>출력순서</label> 												
											<input value="${bodTypeVO.bod_sun}" name="bod_sun" type="text" class="form-control"
											placeholder="출력순서는 숫자로 입력해주세요.">>
										</div>
									</div>
								
								</div>	
								<div class="row">
									
									<div class="col-sm-12">
										
										<div class="buttons">											
											<button type="submit" class="btn btn-warning">Submit</button>
											<a href="#" id="btn_delete" class="btn btn-danger">DELETE</a>
											<a href="/admin/bodtype/list" class="btn btn-primary">LIST ALL</a>
										</div>
									</div>
								</div>
							</form>
						
            
              </div>
              <!-- /.card-body -->
            </div>
            <!-- /.card -->
          </div>
 
    
    </div>
    <!-- ./Content Wrapper. Contains page content -->
    <script>
    //제이쿼리 시작
    $(document).ready(function(){//콜백함수
    	$("#btn_delete").click(function(){
    		if(confirm("정말로 삭제하시겠습니까?")){
    			$("#form_bodtype").attr("action","/admin/bodtype/delete");
    			$("#form_bodtype").submit();
    		}
    	});
    });
    </script>
<%@ include file="../include/footer.jsp" %> 