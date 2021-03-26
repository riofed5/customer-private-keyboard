module.exports = async function (context, req) {
  console.log(req.body);

  return {
    target: "pressButton",
    arguments: [req.body],
  };
};
