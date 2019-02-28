jQuery(function($) {
	var cometd = $.cometd;
	$(document).ready(function() {
		/**
		 * Therefore the code that you put in the _connectionEstablished()
		 * function must be idempotent. In other words, make sure that if the
		 * _connectionEstablished() function is called more than one time, it
		 * will behave exactly as if it is called only once.
		 * 
		 * @returns
		 */
		function _connectionEstablished() {
			$('#body').append('<div>CometD Connection Established</div>');
		}

		function _connectionBroken() {
			$('#body').append('<div>CometD Connection Broken</div>');
		}

		function _connectionClosed() {
			$('#body').append('<div>CometD Connection Closed</div>');
		}

		// Function that manages the connection status with the Bayeux
		// server
		var _connected = false;
		// 检测会话连接是否建立
		function _metaConnect(message) {
			if (cometd.isDisconnected()) {
				_connected = false;
				_connectionClosed();
				return;
			}

			var wasConnected = _connected;
			_connected = message.successful === true;
			if (!wasConnected && _connected) {
				_connectionEstablished();
			} else if (wasConnected && !_connected) {
				_connectionBroken();
			}
		}

		// Function invoked when first contacting the server and
		// when the server has lost the state of this client
		var loop = 0;
		function _metaHandshake(handshake) {
			if (handshake.successful === true) {
				cometd.batch(function() {
					/*cometd.subscribe('/hello', function(message) {
						alert("hello : " + message.data);
						$("#chetd").val($("#chetd").val() + message.data);
					});
					cometd.subscribe('/service/hello', function(message) {
						alert("hellosss : " + message.data);
					});
					cometd.addListener('/service/thisis', function(message) {
						alert("helloListener : " + message.data);
						$("#chetd").val($("#chetd").val() + message.data);
					});*/
					
						cometd.subscribe('/oneBidding', function(message) {
							alert("hello : " + message.data);
							$("#recods").append("++++++")
						});
						cometd.subscribe('/service/oneBiddingback', function(message) {
							alert("个人频道: " + message.data);
							$("#personRecod").append("======");
						});
						cometd.addListener('/service/thisis', function(message) {
							alert("helloListener : " + message.data);
							$("#chetd").val($("#chetd").val() + message.data);
						});

				});
			}
		}

		// Disconnect when the page unloads
		$(window).unload(function() {
			cometd.disconnect(true);
		});

		var cometURL = "http://localhost:8080/ts-web/cometd";
		cometd.configure({
			url : cometURL,
			logLevel : 'debug',
		});
		// 进行握手
		cometd.addListener('/meta/handshake', _metaHandshake);
		// 建立连接
		// cometd.addListener('/meta/connect', _metaConnect);

		cometd.handshake();
		
		cometd.publish("/connected","connected");
	});
	$("#bidding").click(function(){
		
		var message = {};
	/*
		var projectId = $("#projectId").val();
		var mark = $("#mark").val();
		var recod = $("#recod").val();
		
		message.projectId = projectId;
		message.mark = mark;
		message.recod = recod;
	*/
		message.projectId = "00000000000000010857";
		message.mark = "1";
		message.biddingPrice = $("#recod").val();
		$("#recod").val("");
		$("#recod").attr("disabled","disabled");
		cometd.publish('/service/oneBidding', message);//发送给管理员的
		//cometd.publish('/hello', message);
	})
});