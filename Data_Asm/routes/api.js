var express = require('express')
var router = express.Router()
//Them model
const User = require('../models/user')
const Shoses = require('../models/shose')
const Transproter = require('../config/common/email')
const Upload = require('../config/common/upload')
const Cart = require("../models/cart")
const Calendar = require("../models/calendar")
const Spheart = require("../models/sphear")


//add shose
router.post('/add-shose', Upload.single('image_anh'), async (req, res) => {
  try {
    const data = req.body
    const { file } = req
    const urlImage = `${req.protocol}://${req.get("host")}/uploads/${file.fieldname}`
    const newShose = new Shoses({
      shoseName: data.shoseName,
      description: data.description,
      price: data.price,
      image_anh: urlImage
    })
    const result = await newShose.save();
    if (result) {
      res.json({
        'status': 200,
        'messenger': 'Them thanh cong',
        'data': data
      })
    } else {
      res.json({
        'status': 400,
        'messenger': 'Them that bai',
        'data': []
      })
    }
  } catch (error) {
    console.log(error);
  }
})
//get shose
router.get('/get-list-shose', async (req, res) => {
  try {
    const data = await Shoses.find().populate()
    res.json({
      'status': 200,
      'messenger': "Danh sách shose",
      'data': data
    })
  } catch (error) {
    console.log(error);
  }
})
//search shose
router.get('/search-shose', async (req, res) => {
  try {
    const key = req.query.key

    const data = await Shoses.find({ shoseName: { "$regex": key, "$options": "i" } })
      .sort({ createdAt: -1 });

    if (data) {
      res.json({
        "status": 200,
        "messenger": "Thành công",
        "data": data
      })
    } else {
      res.json({
        "status": 400,
        "messenger": "Lỗi, không thành công",
        "data": []
      })
    }
  } catch (error) {
    console.log(error);
  }
})
//get user
router.get('/get-list-user', async (req, res) => {
  try {
    const data = await User.find().populate()
    res.json({
      'status': 200,
      'messenger': "Danh sách shose",
      'data': data
    })
  } catch (error) {
    console.log(error);
  }
})
//get user id
router.get("/get_user_id/:id", async (req, res) => {
  try {
    const { id } = req.params
    const data = await User.findById(id).populate()
    res.json({
      'status': 200,
      'messenger': "Danh sách billdetails",
      'data': data
    })
  } catch (error) {
    console.log(error);
  }
})
//dk email
router.post('/register-send-email', Upload.single(), async (req, res) => {
  try {
    const data = req.body
    const newUser = User({
      email: data.email,
      password: data.password,
      fullname: data.fullname,

    })
    const result = await newUser.save()
    if (result) {
      const mailOptions = {
        from: "cuondang5124@gmail.com",
        to: result.email,
        subject: "Đăng ký thành công",
        text: "Cảm ơn bạn đã đăng ký"
      }
      await Transproter.sendMail(mailOptions)
      res.json({
        'status': 200,
        'messenger': "Thêm thành công",
        'data': result
      })
    } else {
      res.json({
        'status': 400,
        'messenger': "Lỗi, Thêm thất bại",
        'data': []
      })
    }
  } catch (error) {
    console.log(error);
  }
})
router.put("updata-pass-id/:id", Upload.single(),async (req, res) => {
  try {
    const { id } = req.params
    const data = req.body
    const updataUser = await User.findByIdAndUpdate(id)
    let result = null
    if (updataUser) {
      updataUser.password = data.password ?? updataUser.password
      result = await updataUser.save()
      if (result) {
        res.json({
          'status': 200,
          'messenger': "Thêm thành công",
          'data': result
        })
      } else {
        res.json({
          'status': 400,
          'messenger': "Lỗi, thêm thất bại",
          'data': []
        })
      }
    }

  } catch (error) {
    console.log(error);
  }
})
router.put('/update-user-id/:id', Upload.single("hinh_anh_user"), async (req, res) => {
  try {
    const { id } = req.params
    const data = req.body
    const { file } = req
    const url_anh = `${req.protocol}://${req.get("host")}/uploads/${file.filename}`
    const updataUser = await User.findByIdAndUpdate(id)
    let result = null
    if (updataUser) {
      updataUser.fullname = data.fullname ?? updataUser.fullname,
        updataUser.hinh_anh_user = url_anh,
        updataUser.ngay_sinh_user = data.ngay_sinh_user ?? updataUser.ngay_sinh_user,
        updataUser.sdt_user = data.sdt_user ?? updataUser.sdt_user,
        updataUser.dia_chi_user = data.dia_chi_user ?? updataUser.dia_chi_user
      result = await updataUser.save()
      if (result) {
        res.json({
          'status': 200,
          'messenger': "Thêm thành công",
          'data': result
        })
      } else {
        res.json({
          'status': 400,
          'messenger': "Lỗi, thêm thất bại",
          'data': []
        })
      }
    }
  } catch (error) {

  }
})
//login
const JWT = require('jsonwebtoken')
const SECRETKEY = "FPTPOLYTEACHNIC"
router.post("/login", async (req, res) => {
  try {
    const { email, password } = req.body
    const user = await User.findOne({ email, password })
    if (user) {
      const token = JWT.sign({ id: user._id }, SECRETKEY, { expiresIn: "1d" })
      const refreshToken = JWT.sign({ id: user._id }, SECRETKEY, { expiresIn: "1d" })
      res.json({
        "status": 200,
        "messenger": "Dang nhap thanh cong",
        "data": user,
        "toKen": token,
        "refreshToken": refreshToken
      })
    } else {
      res.json({
        "status": 400,
        "messenger": "Dang nhap that bai",
        "data": [],

      })
    }
  } catch (error) {
    console.log(error);
  }
})
//get Cart
router.get("/get_cart", async (req, res) => {
  try {
    const data = await Cart.find().populate()
    res.json({
      'status': 200,
      'messenger': "Danh sách billdetails",
      'data': data
    })
  } catch (error) {
    console.log(error);
  }
})
//add Cart
router.post('/add-cart', async (req, res) => {
  try {
    const data = req.body
    const newCart = new Cart({
      id_User_cart:data.id_User_cart,
      id_Shose_cart: data.id_Shose_cart,
      ten_shose_cart: data.ten_shose_cart,
      gia_shose_cart: data.gia_shose_cart,
      hinh_anh_cart: data.hinh_anh_cart,
    })
    const result = await newCart.save();
    if (result) {
      res.json({
        'status': 200,
        'messenger': 'Them thanh cong',
        'data': data
      })
    } else {
      res.json({
        'status': 400,
        'messenger': 'Them that bai',
        'data': []
      })
    }
  } catch (error) {
    console.log(error);
  }
})
//delete Cart
router.delete('/delete-cart-id/:id', async (req, res) => {
  try {
    const { id } = req.params
    const result = await Cart.findByIdAndDelete(id)
    if (result) {
      res.json({
        'status': 200,
        'messenger': "Xoa thành công",
        'data': result
      })
    } else {
      res.json({
        'status': 400,
        'messenger': "Lỗi, Xoa thất bại",
        'data': []
      })
    }
  } catch (error) {
    console.log(error);
  }
})
//get Cart id user
router.get("/get_cart_id/:id_User_cart", async (req, res) => {
  try {
    const { id_User_cart } = req.params;
    const data = await Cart.find({ id_User_cart });
    res.json({
      'status': 200,
      'messenger': "Danh sách billdetails",
      'data': data
    })
  } catch (error) {
    console.log(error);
  }
})
//get Calendar
router.get("/get_caledar", async (req, res) => {
  try {
    const data = await Calendar.find().populate()
    res.json({
      'status': 200,
      'messenger': "Danh sách billdetails",
      'data': data
    })
  } catch (error) {
    console.log(error);
  }
})
//add Calendar
router.post('/add-calendar', async (req, res) => {
  try {
    const data = req.body
    const newCalendar = new Calendar({
      id_User_Calendar: data.id_User_Calendar,
      id_Shose_Calendar: data.id_Shose_Calendar,
      id_cart_Calendar: data.id_cart_Calendar,
      ten_shose_Calendar: data.ten_shose_Calendar,
      gia_shose_Calendar: data.gia_shose_Calendar,
      hinh_anh_Calendar: data.hinh_anh_Calendar,
      date_Calendar: data.date_Calendar,
    })
    const result = await newCalendar.save();
    if (result) {
      res.json({
        'status': 200,
        'messenger': 'Them thanh cong',
        'data': data
      })
    } else {
      res.json({
        'status': 400,
        'messenger': 'Them that bai',
        'data': []
      })
    }
  } catch (error) {
    console.log(error);
  }
})
//get Calendar id
router.get("/get_calendar_id/:id_User_Calendar", async (req, res) => {
  try {
    const { id_User_Calendar } = req.params;
    const data = await Calendar.find({ id_User_Calendar });
    res.json({
      'status': 200,
      'messenger': "Danh sách billdetails",
      'data': data
    })
  } catch (error) {
    console.log(error);
  }
})
//add shoe heart
router.post('/add-heart', async (req, res) => {
  try {
    const data = req.body
    const newHeart = new Spheart({
      id_User_heart:data.id_User_heart,
      id_Shose_heart:data.id_Shose_heart,
      shose_Name_heart: data.shose_Name_heart,
      description_heart: data.description_heart,
      price_heart: data.price_heart,
      image_anh_heart: data.image_anh_heart,
    })
    const result = await newHeart.save();
    if (result) {
      res.json({
        'status': 200,
        'messenger': 'Them thanh cong',
        'data': data
      })
    } else {
      res.json({
        'status': 400,
        'messenger': 'Them that bai',
        'data': []
      })
    }
  } catch (error) {
    console.log(error);
  }
})
//get heart id_user
router.get("/get_heart_id/:id_User_heart", async (req, res) => {
  try {
    const { id_User_heart } = req.params;
    const data = await Spheart.find({ id_User_heart });
    res.json({
      'status': 200,
      'messenger': "Danh sách billdetails",
      'data': data
    })
  } catch (error) {
    console.log(error);
  }
})
//delete haểt id
router.delete('/delete-heart-id/:id', async (req, res) => {
  try {
    const { id } = req.params
    const result = await Spheart.findByIdAndDelete(id)
    if (result) {
      res.json({
        'status': 200,
        'messenger': "Xoa thành công",
        'data': result
      })
    } else {
      res.json({
        'status': 400,
        'messenger': "Lỗi, Xoa thất bại",
        'data': []
      })
    }
  } catch (error) {
    console.log(error);
  }
})
//get haert
router.get("/get_heart", async (req, res) => {
  try {
    const data = await Spheart.find().populate()
    res.json({
      'status': 200,
      'messenger': "Danh sách billdetails",
      'data': data
    })
  } catch (error) {
    console.log(error);
  }
})

module.exports = router;