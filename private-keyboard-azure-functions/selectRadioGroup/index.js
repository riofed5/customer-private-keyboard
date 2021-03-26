module.exports = async function (context, req) {
  console.log(req.body);

  return {
    target: "selectRadioGroup",
    arguments: [req.body],
  };
};
