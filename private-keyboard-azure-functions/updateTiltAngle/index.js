module.exports = async function (context, req) {
  console.log(req.body);

  return {
    target: "updateTiltAngle",
    arguments: [req.body],
  };
};
