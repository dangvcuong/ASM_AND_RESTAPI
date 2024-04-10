const nodemailer = require("nodemailer");

const transporter = nodemailer.createTransport({
    service: "gmail",
    auth: {
        user: "cuondang5124@gmail.com",
        pass: "123456789"
    }
});

module.exports = transporter; 