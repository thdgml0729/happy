function checkAllDel(bool){
	var chks = document.getElementsByName("chkVal");
//	alert(chks.length);
	for (var i = 0; i < chks.length; i++) {
		chks[i].checked = bool;
	}
}

function chkBox(){ //class=table안에를 ajax로 처리할때에 form의 action이 걸릴수있어서 action을 #으로 처리했다.
//	alert("작동");
	var chks = document.getElementsByName("chkVal");
	var c = 0;
	for (var i = 0; i < chks.length; i++) {
		if(chks[i].checked){
			c++;
		}
	}
	if(c>0){
		var doc = document.getElementById("frm");
//		var doc = document.forms[0];
//		var doc = document.(formname)
		doc.action = "./multidel.do";
		return true;
	}else{
		swal("게시글 삭제 오류","선택된 글이 없습니다. ");
		return false;
	}
	return false;
}
/////////////page
function pageList(){
	// hidden으로 처리했던 애
	var index = document.getElementById("index");
	var pageNum = document.getElementById("pageNum");
	var listNum = document.getElementById("listNum");
	
	index.value = 0;
	pageNum.value = 1;
	listNum.value= document.getElementById("list").value;
	
	pageAjax();
}



//페이지 숫자 눌렀을때
function pageIndex(pageNum){
//	alert(pageNum);
	var index = document.getElementById("index");
	index.value = pageNum-1;
//	==$("#index").val(pageNum-1);
	
	pageAjax(); // type은 function
}

//pageFrist(${row.pageList},${row.pageList})
function pageFrist(num, pageList){
//	var index= 0;
//	var pageNum	= 1;
	
	var pageNum = document.getElementById("pageNum");
	var index = document.getElementById("index");
	
	pageNum.value = 1;
	index.value = 0;
	
	alert(pageNum.value);//var pageNum	= 1;가 찍히기 때문에 object로 나온다. 
	alert(index.value);
	
	pageAjax();
}
//pagePre(${row.pageNum},${row.pageList})
function pagePre(num, pageList){
	if(0<num-pageList){ // 한페이지가 더 있다.
		num -= pageList;
		varpageNum  = document.getElementById("pageNum");
		var index = document.getElementById("index");
		
		pageNum.value = num;
		index.value = num-1;
	}
	pageAjax();
}

//pageNext(${row.pageNum},${row.total},${row.listNum} ,${row.pageList})
function pageNext(num, total, listNum, pageList){
//	var index = Math.ceil(total/listsNum); //5개 묶어서 처리 : 묶음 // 묶음 40/5 => 8
//	var max = Math.ceil(index/pageList); // 글의 갯수 // 글의 갯수 8/5 =>2
//
//	if(max*pageList>num+pageList){// 다음페이지 
//		num += pageList;
//		var pageNum  = document.getElementById("pageNum");
//		var index = document.getElementById("index");
//		
//		pageNum.value = num;
//		index.value = num-1;
//	}
//	pageAjax();
	var index = Math.ceil(total/listNum); //묶음 40/5 => 8
	var max = Math.ceil(index/pageList); // 글의 갯수 8/5 => 2
	
	if(max*pageList > num+pageList){
		num += pageList;
		
		var pageNum = document.getElementById("pageNum");
		var index = document.getElementById("index");
		
		pageNum.value = num;
		index.value = num-1;
	}
	pageAjax();
	
}

//pageLast(${row.pageNum},${row.total},${row.listNum} ,${row.pageList})
function pageLast(num, total, listNum, pageList){
//	var i = document.getElementById("index").value;
//	var max = Math.ceil(total/pageList);
//	var idx = Math.ceil(max/listNum);
//	
//	var index = Math.ceil(total/listNum);
//	var max = Math.ceil(index/pageList);
//	
//	
//	while(max*pageList > num+pageList){
//		num+=pageList;
//	}
//	
//	var pageNum = document.getElementById("pageNum");
//	var index = document.getElementById("index");
//	
//	pageNum.value=num;
//	index.value=num-1;
//	
////	pageNum.value = max - idx +1;
////	index.value = max-1;
//	
//	pageAjax();
	var idx = Math.ceil(total/listNum);
	var max = Math.ceil(idx/pageList);
	
	while(max*pageList > num+pageList){
		num += pageList
	}
	
	var pageNum = document.getElementById("pageNum");
	var index = document.getElementById("index");
	
	pageNum.value= num;
//	index.value = num-1;
	index.value = idx-1;
	
//	pageNum.value = max - idx+1;
//	index.value = max-1;
	
	pageAjax();
}


////////////////////////////
//페이지 처리를 위한 공통Ajax//
////////////////////////////

var pageAjax = function(){
//	alert("아작스 작동 예정");
	var obj = document.getElementById("index").value;
	$.ajax({
		url:"./paging.do",
		type: "post",
		async: true,
		data: $("#frm").serialize(),  //"index="+obj // JSON.stringify// data에 속해있는 객체
		dataType : "json",
		success:function(msg){ //msg는 받은거
//			alert(msg.lists[0].seq);
//			alert(msg.row.total);
			$.each(msg, function(key,value){
				var htmlTable = "";
				var n= $(".table tr:eq(0) th").length; // tr첫번째거의 th갯수 ==tr:eq(0).child
//				alert(n);
				if(key=="lists"){ // table을 만들어 줌
//					alert("2");
					htmlTable+="<tr>"+	
					"<th><input type='checkbox' onclick='checkAllDel(this.checked)'></th>"+	
					"<th>글번호</th>"+	
					"<th>제목</th>"+	
					"<th>작성자</th>"+	
					"<th>조회수</th>";
					
					if(n == 7){		
						htmlTable +="<th>삭제여부</th>";
					}
//					"<c:if test="${fn:trim(mem.auth) eq 'A'}">"+"</c:if>";
					
					htmlTable+="<th>작성일</th></tr>";
					
					//내용을 출력해 준다.(lists:[{key,value},{},{}]) //lists: key, [{key,value},{},{}]:value
					$.each(value, function(key,fri){
						htmlTable+="<tr>" +
								"<td>" +
								"<input type='checkbox' name='chkVal' value='"+fri.seq+"'></td>" +
										"<td>"+fri.seq+"</td>" +
										"<td><div class='panel-heading'>" +
										"<a data-toggle='collapse' data-parent='#accordion' " +
										"href='#collapse"+fri.seq+"' " +
										"onclick='collapse(\""+fri.seq+"\")'>"+fri.title+"</a>" +
										"</div></td><td>"+fri.id+"</td><td>"+fri.readcount+"</td>";
									if(n==7){
										htmlTable+="<td>"+fri.delflag+"</td>";
									}	
									htmlTable+="<td>"+fri.regdate+"</td></tr>";
									
									htmlTable+="<tr>" +
											"<td colspan=\""+n+"\">" +
											"<div id='collapse"+fri.seq+"'" +
											"class='panel-collapse collapse' class aria-expanded='false' style='height:0px'>" +
											"<div class='form-group'><label>내용</label><br>" +
											"<textarea row='7' class='form-control' readonly='readonly'>"+fri.content+"</textarea></div>" +
											"<div class='form-group'>";
									if(fri.id==fri.memid){	
										htmlTable+="<input class='btn btn-primary' type='button' value='글수정하기' onclick='modify(\""+fri.seq+"\")'>";
									}
									if(fri.id==fri.memid || n==7){
										htmlTable+="<input class='btn btn-primary' type='button' value='글삭제' onclick='del(\""+fri.seq+"\")'>";	
									}
									if(n!=7){
										htmlTable+="<input class='btn btn-primary' type='button' value='답글' onclick='reply(\""+fri.seq+"\")'>";
									}
										htmlTable+="</div></div></td></tr>";
					});
				}else{ // key=row는 paging을 만들어줌
					htmlTable+="<li><a href='#' onclick='pageFrist("+value.pageList+","+value.pageList+")'>&laquo;</a></li>";
					htmlTable+="<li><a href='#' onclick='pagePre("+value.pageNum+","+value.pageList+")'>&lsaquo;</a></li>";
					for (var i = value.pageNum; i <= value.count; i++) { // 초기값 왜 value인지 알아야한다. 
						htmlTable+="<li><a href='#' onclick='pageIndex("+i+")'>"+i+"</a></li>";
					}
//					htmlTable+="<c:forEach var='i' begin="${row.pageNum}" end="${row.count}" step='1'>";
//					htmlTable+="<li><a href='#' onclick='pageIndex(${i})'>${i}</a></li>";
//					htmlTable+="</c:forEach>";
					htmlTable+="<li><a href='#' onclick='pageNext("+value.pageNum+","+value.total+","+value.listNum+","+value.pageList+")'>&rsaquo;</a></li>";
					htmlTable+="<li><a href='#' onclick='pageLast("+value.pageNum+","+value.total+","+value.listNum+","+value.pageList+")'>&raquo;</a></li>";
				}
				if(key=="lists"){
					$("#table>tbody").html(htmlTable);
				}else{
					$(".pagination").html(htmlTable);	
				}
				
			});//for문
			// each(사용할 객체, 사용할 기능)
		}
	});
}

// 수정 삭제 답글 관려 modal 기능
function modify(seq){
	alert(seq);
	ajaxModify(seq);
	$("#modify").modal();
}

var ajaxModify = function(seq){
//	alert("ajax메소드"+seq);
	$.ajax({
		url : "./modifyForm.do",
		type : "post",
		data : {"seq": seq}, //json형태
		dataType : "json",
		success : function(obj){
//			alert(obj.title);
			var htmlModal = "<input type='hidden' name='seq' value='"+obj.seq+"'>"+
			  	"<input type='hidden' name='id' value='"+obj.id+"'>"+
			  	"<div class='form-group'>"+
			  		"<label>글번호</label>"+
			  		"<p class=form-control'>"+
			  		"<strong>"+obj.seq+"</strong>"+
			  		"</p>"+
			  	"</div>"+
			  	"<div class='form-group'>"+
			  		"<label>조회수</label>"+
			  		"<p class='form-control'>"+
			  			"<strong>"+obj.readcount+"</strong>"+
			  		"</p>"+
			  	"</div>"+
			  	"<div class='form-group'>"+
			  		"<label>작성일</label>"+
			  		"<p class='form-control'>"+
			  			"<strong>"+obj.regdate+"</strong>"+
			  		"</p>"+
			  	"</div>"+ 	
			  	"<div class='form-group'>"+
			  		"<label>제목</label>"+
			  		"<input type='text' class='form-control' id='motitle' name='title' value='"+obj.title+"' required='required'>"+
			  	"</div>"+
			  	
			  	"<div class='form-group'>"+
			  		"<label>내용</label>"+
			  		"<textarea rows='7' class='form-control' id='mocontent' name='content'>"+obj.content+"</textarea>"+
			  "	</div>"+
			  	
			  	"<div class='modal-footer'>"+
			  		"<input class='btn btn-success' type='button' value='글수정' onclick='update()'>"+
			  		"<input class='btn btn-success' type='reset' value='초기화'>"+
			  		"<button type='button' class='btn btn-default' data-dismiss='modal'>닫기</button>"+
			  	"</div>";
			$("#frmModify").html(htmlModal);
			
		}
	});
}

function update(){
	var frm = document.getElementById("frmModify");
	frm.action = "./modify.do";
	var title = document.getElementById("motitle").value;
	var content = $("#mocontent").val();
	
//	alert(title+"/"+content);
	
	if(title==""||content==""){
		swal("글 수정 오류", "제목 혹은 내용을 입력해 주세요");
	}else{
		frm.submit();
	}
}

//삭제
function del(seq){
	location.href="./del.do?seq="+seq;
}

//답글 관련 modal 기능
function reply(seq){
//	alert(seq);
	ajaxReply(seq);
	$("#reply").modal();
}

var ajaxReply = function(seq){
	$.ajax({
		url : "./replyForm.do",
		type : "post",
		data : {"seq":seq},
		async : true,
		dataType : "json",
		success : function(obj){//<!--form-group안에 form-controller있어야한다.  -->
//			alert(obj.title);
			var replyHTML = "<input type='hidden' name='seq' value='"+obj.seq+"'>"+
		"<div class='form-group'>"+
			"<label>답글할 글번호</label><br>"+
			"<p class='form-control'>"+obj.seq+"</p>"+
		"</div>"+
		"<div class='form-group'>"+
			"<label>답글할 글 조회수</label>"+
			"<p class='form-control'>"+obj.readcount+"</p>"+
		"</div>"+
		"<div class='form-group'>"+
			"<label>답글할 글 작성일</label>"+
			"<p class='form-control'>"+obj.regdate+"</p>"+
		"</div>"+
		"<div class='form-group'>"+
			"<label>답글할 글 작성자</label>"+
			"<p class='form-control'>"+obj.memid+"</p>"+
		"</div>"+
		"<div class='form-group'>"+
			"<label id='titl'>제목(원본)</label>"+
			"<input type='text' class='form-control' id='title' name='title' value='"+obj.title+"' required='required' onclick='chk()'>"+
		"</div>"+
		"<div class='form-group'>"+
			"<label id='con'>내용(원본)</label>"+
			"<input type='hidden' id='hiddenContent' value='"+obj.content+"'>"+
			"<textarea id='txtArea' rows='7' class='form-control' name='content'>"+obj.content+"</textarea>"+
		"</div>"+
		"<div class='modal-footer'>"+
			"<input type='button' class='btn btn-info' value='답글작성' onclick='replied()'>"+
			"<input type='reset' class='btn btn-info' value='초기화' onclick='titleReset()'>"+
			"<button type='button' class='btn btn-default' data-dismiss='modal'>닫기</button>";
			
			$("#frmReply").html(replyHTML);
		}
	});
}

function replied(){
//	alert("전송");
	var frm = document.getElementById("frmReply");
	frm.action = "./reply.do";
	var title = document.getElementById("title").value;
	var txtArea = document.getElementById("txtArea").value;
	
//	alert(title+":"+txtArea);
	
	if(title==""||txtArea==""){
		swal("답글작성 오류","제목 혹은 내용을 작성해 주세요");
	}else{
		var titl = document.getElementById("titl").innerHTML;
		if(titl == "제목"){
			frm.submit();
		}else{
			swal("답글작성 오류","답글을 먼저 작성해 주세요"	);
		}
	}
	
}

function chk(){
//	alert("작동");
	var titl = document.getElementById("titl");
	var title = document.getElementById("title");
	var con = document.getElementById("con");
	var txtArea = document.getElementById("txtArea");
	var hiddenContent = document.getElementById("hiddenContent"); //njsqps
	//jquery는 .txt
	if(hiddenContent.value == txtArea.value){
		con.innerHTML = "내용";
		titl.innerHTML= "제목";

		txtArea.value = "";
		title.value ="";
	}
}

function titleReset(){
	document.getElementById("titl").innerHTML = "제목(원본)";
	document.getElementById("con").innerHTML = "내용(원본)";
}
