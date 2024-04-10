const mongoose = require('mongoose')
const Sheme = mongoose.Schema;

const Cart = new Sheme({
    id_User_cart: { type: String },
    id_Shose_cart: { type: String },
    ten_shose_cart: { type: String },
    gia_shose_cart: { type: Number },
    hinh_anh_cart: { type: String }
}, {
    timestamps: true
})
module.exports = mongoose.model('cart', Cart)