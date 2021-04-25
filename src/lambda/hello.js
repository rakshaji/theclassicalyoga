// exports.handler = async function (event, context) {
//     // your server-side functionality

//     // generated_signature = hmac_sha256(order_id + "|" + razorpay_payment_id, secret); 
//     // if (generated_signature == razorpay_signature) 
//     // { 
//     //     //payment is successful 
//     // }
// }

exports.handler = (event, context, callback) => {
    callback(null, {
      statusCode: 200,
      body: 'Hello, world!',
    });
  };