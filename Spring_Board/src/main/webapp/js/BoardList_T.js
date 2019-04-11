function checkAllDel(bool){
	var chks =  document.getElementsByName("chkVal");
//	alert(chks.length);
	for (var i = 0; i < chks.length; i++) {
		chks[i].checked = bool;
	}
}


function chkBox(){
//	alert("작동");
	var chks =  document.getElementsByName("chkVal");
	var c = 0;
	for (var i = 0; i < chks.length; i++) {
		if(chks[i].checked){
			c++;
		}
	}
	if(c>0){
//		var doc = document.getElementById("frm");
//		var doc = document.forms[0];
		var doc =document.frm;
		doc.action = "./multidel.do";
		return true;
	}else{
		swal("게시글 삭제 오류","선택된 글이 없습니다.");
		return false;
	}
}
//////////////////// page 처리 

// 페이지 숫자 눌렀을때
function pageIndex(pageNum){
//	alert(pageNum);
	var index = document.getElementById("index");
	index.value = pageNum-1;
	
//	$("#index").val(pageNum-1);
	pageAjax();
}

//pageFrist(${row.pageList},${row.pageList})
function pageFrist(num, pageList){
//		var index = 0;
//		var pageNum = 1;
		
		var pageNum = document.getElementById("pageNum");
		var	index = document.getElementById("index");
		
		pageNum.value = 1;
		index.value = 0;
		
//		alert(pageNum.value);
//		alert(index.value);
		
		pageAjax();
		
}


//pagePre(${row.pageNum},${row.pageList})
function pagePre(num, pageList){
	if(0<num-pageList){
		num -= pageList;
		var pageNum = document.getElementById("pageNum");
		var index = document.getElementById("index");
		
		pageNum.value = num;
		index.value = num-1;
	}
	
	pageAjax();
}


//pageNext(${row.pageNum},${row.total},${row.listNum},${row.pageList})
function pageNext(num, total, listNum, pageList){
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

//pageLast(${row.pageNum},${row.total},${row.listNum},${row.pageList})
function pageLast(num, total, listNum, pageList){
//	var max = Math.ceil(total/pageList); // 21/ 4
//	var idx = Math.ceil(max/listNum); // 4/5 
	
	var index = Math.ceil(total/listNum);
	var max = Math.ceil(index/pageList);
	
	while(max*pageList > num+pageList){
		num += pageList
	}
	
	var pageNum = document.getElementById("pageNum");
	var index = document.getElementById("index");
	
	pageNum.value= num;
	index.value = num-1;
	
//	pageNum.value = max - idx+1;
//	index.value = max-1;
	
	pageAjax();
	
}


  ///////////////////////////
  // 페이지 처리를 위한 공통 Ajax //
  //////////////////////////

var pageAjax = function(){
//	alert("아작스 작동 예정");
//	var obj = docuement.getElementById("index").value;
	$.ajax({
		url : "./paging.do",
		type : "post",
		async : true,
		data :  $("#frm").serialize(),    //"index="+ obj  // JSON.stringify
		dataType : "json",
		success: function(msg){
//			alert(msg.lists[0].seq);
//			alert(msg.row.total);
			$.each(msg,function(key,value){
				var htmlTable = "";
				var n = $(".table tr:eq(0) th").length;
//				alert(n);
				
				if(key=="lists"){ // table을 만들어 줌
					
					htmlTable += "<tr>"+
					"<th><input type='checkbox' onclick='checkAllDel(this.checked)'> </th>"+
					"<th>글번호</th>"+
					"<th>제목</th>"+
					"<th>작성자</th>"+
					"<th>조회수</th>";
		if(n == 7){
		htmlTable +="<th>삭제여부</th>";
		}
		htmlTable +="<th>작성일</th></tr>";
		
		// 내용을 출력해 준다(lists:[{key,value},{},{}])
		$.each(value,function(key,fri){
			
			htmlTable +="<tr>" +
					"<td>" +
					"<input type='checkbox' name='chkVal' value='"+fri.seq+"'></td>" +
					"<td>"+fri.seq+"</td>" +
					"<td><div class='panel-heading'>" +
					"<a data-toggle='collapse' data-parent='#accordion' " +
					"href='#collapse"+fri.seq+"' " +
					"onclick='collapse(\""+fri.seq+"\")'>"+fri.title+"</a>" +
					"</div></td><td>"+fri.id+"</td><td>"+fri.readcount+"</td>";
			if(n==7){
				htmlTable += "<td>"+fri.delflag+"</td>";
			}
				htmlTable += "<td>"+fri.regdate+"</td></tr>";
					
			htmlTable += "<tr>" +
					"<td colspan=\""+n+"\">" +
					"<div id='collapse"+fri.seq+"'" +
					" class='panel-collapse collapse' aria-expanded='false' style='height:0px;'>" +
					"<div class='form-group'><label>내용</label><br>" +
					"<textarea row='7' class='form-control' readonly='readonly'>"+fri.content+"</textarea></div>" +
					"<div class='form-group'>";
			if(fri.id == fri.memid){
				htmlTable +="<input class='btn btn-primary' type='button' value='글수정하기' onclick='modify(\""+fri.seq+"\")'>" ;
			}
			if(fri.id == fri.memid || n==7){
				htmlTable +="<input class='btn btn-primary' type='button' value='글삭제' onclick='del(\""+fri.seq+"\")'>" ;
			}
			if(n!=7){
				htmlTable +="<input class='btn btn-primary' type='button' value='답글' onclick='reply(\""+fri.seq+"\")'>" ;
			}
			htmlTable +="</div></div></td></tr>";		
		});
					
				}else{ // key=row는 paging를 만들어 줌
					
					htmlTable +="<li><a href='#' onclick='pageFrist("+value.pageList+","+value.pageList+")'>&laquo;</a></li>";
					htmlTable +="<li><a href='#' onclick='pagePre("+value.pageNum+","+value.pageList+")'>&lsaquo;</a></li>";
					
					for (var i =value.pageNum ; i <= value.count; i++) {
						htmlTable +="<li><a href='#' onclick='pageIndex("+i+")'>"+i+"</a></li>";
					}
									
					htmlTable +="<li><a href='#' onclick='pageNext("+value.pageNum+","+value.total+","+value.listNum+","+value.pageList+")'>&rsaquo;</a></li>";
					htmlTable +="<li><a href='#' onclick='pageLast("+value.pageNum+","+value.total+","+value.listNum+","+value.pageList+")'>&raquo;</a></li>";
				}
				
				if(key=="lists"){
					$("#table > tbody").html(htmlTable);
				}else{
					$(".pagination").html(htmlTable);
				}
				
			});
			
		}
	});
	
}





