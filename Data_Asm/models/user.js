const mongoose = require('mongoose')
const Sheme = mongoose.Schema;

const User = new Sheme({
    email:{type: String, unique: true ,maxLength: 255},
    password:{ type: String, unique: true, maxLength: 255},
    fullname:{type: String},
    hinh_anh_user:{type:String},
    ngay_sinh_user:{type:String},
    dia_chi_user:{type:String},
    sdt_user:{type:String}
},{
    timestamps:true
})
module.exports = mongoose.model('user',User)