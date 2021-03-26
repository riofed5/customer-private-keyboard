module.exports = async function (context, req) {
  console.log(req.body);

  return {
    target: "confirmQRScan",
    arguments: [req.body],
  };
};
