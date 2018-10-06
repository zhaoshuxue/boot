<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>3d book page</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/jquery-2.1.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/jslib/booklet/jquery.easing.1.3.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/jslib/booklet/jquery.booklet.1.1.0.min.js" type="text/javascript"></script>
	
	<link href="${pageContext.request.contextPath}/jslib/booklet/jquery.booklet.1.1.0.css" type="text/css" rel="stylesheet" />
	
	<link rel="stylesheet" href="style.css" type="text/css"/>
</head>
<body>
	<div style="text-align:center;clear:both"></div>

	<div class="book_wrapper">
			<a id="next_page_button"></a>
			<a id="prev_page_button"></a>
			<div id="loading" class="loading">Loading pages...</div>
			<div id="mybook" style="display:none;">
				<div class="b-load">
					<div><img src="http://highness.qiniudn.com/082e8fc967c441aa8e128a1cc3aaae9d.jpg" alt=""/></div>
					
					<div><img src="http://highness.qiniudn.com/0ffe2f515374457ab3d408c132635da0.gif" alt="" /></div>
					
					<div><img src="http://highness.qiniudn.com/10c16e6cdddb44a1a6a7b0c7e2c0ca13.gif" alt="" /></div>
					
					<div><img src="http://highness.qiniudn.com/1a7390f63aa448609f2152237d8e90bd.jpg" alt="" /></div>
					
					<div><img src="http://highness.qiniudn.com/22e72d03c3fd4358994ec819e0eccf8d.jpg" alt="" /></div>
					
					<div><img src="http://highness.qiniudn.com/31ebaaa9102a45d79f17663f66dce1de.jpg" alt="" /></div>
					
					<div><img src="http://highness.qiniudn.com/3b13f7df631f4661a748186fc8bbb67f.gif" alt="" /></div>
					
					<div><img src="http://highness.qiniudn.com/3ed25a276dee42aaa9f9b1e518d0473e.gif" alt="" /></div>
					<%for(int i=0; i<10; i++){ %>
					<div><img src="http://highness.qiniudn.com/4c33f77ac9b54ad7bb999b112f1cc2e7.jpg" alt="" /></div>
					
					
					<div><img src="http://highness.qiniudn.com/51facb03425b42ab96b74561ff72ad50.gif" alt="" /></div>
					<%} %>
				</div>
			</div>
	</div>

</body>
<script type="text/javascript">
			$(function() {
				var $mybook 		= $('#mybook');
				var $bttn_next		= $('#next_page_button');
				var $bttn_prev		= $('#prev_page_button');
				var $loading		= $('#loading');
				var $mybook_images	= $mybook.find('img');
				var cnt_images		= $mybook_images.length;
				var loaded			= 0;
				//preload all the images in the book,
				//and then call the booklet plugin

				$mybook_images.each(function(){
					var $img 	= $(this);
					var source	= $img.attr('src');
					$('<img/>').load(function(){
						++loaded;
						if(loaded == cnt_images){
							$loading.hide();
							$bttn_next.show();
							$bttn_prev.show();
							$mybook.show().booklet({
								name:               null,                            
								width:              800,                             
								height:             500,                           
								speed:              600,                            
								direction:          'LTR',                          
								startingPage:       0,                               
								easing:             'easeInOutQuad',                 
								easeIn:             'easeInQuad',                    
								easeOut:            'easeOutQuad',                   

								closed:             true,                           
								closedFrontTitle:   null,                            
								closedFrontChapter: null,                            
								closedBackTitle:    null,                            
								closedBackChapter:  null,                            
								covers:             false,                           

								pagePadding:        10,                             
								pageNumbers:        true,                            

								hovers:             false,                            
								overlays:           false,                            
								tabs:               false,                           
								tabWidth:           60,                              
								tabHeight:          20,                              
								arrows:             false,                           
								cursor:             'pointer',                       

								hash:               false,                           
								keyboard:           true,                            
								next:               $bttn_next,          			
								prev:               $bttn_prev,          			

								menu:               null,                            
								pageSelector:       false,                           
								chapterSelector:    false,                           

								shadows:            true,                            
								shadowTopFwdWidth:  166,                             
								shadowTopBackWidth: 166,                             
								shadowBtmWidth:     50,                              

								before:             function(){},                    
								after:              function(){console.log('asdfasf');/*test();*/}                     
							});
						}
					}).attr('src',source);
				});
				
			});

</script>
</html>