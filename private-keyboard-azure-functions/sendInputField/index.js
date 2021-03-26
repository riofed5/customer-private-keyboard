module.exports = async function (context, req) {
  console.log(req.body);

  return {
    target: "sendInputField",
    arguments: [req.body],
  };
};
