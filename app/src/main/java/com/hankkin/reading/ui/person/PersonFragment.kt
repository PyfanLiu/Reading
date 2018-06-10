package com.hankkin.reading.ui.person

import android.content.Intent
import com.hankkin.reading.R
import com.hankkin.reading.base.BaseMvpFragment
import com.hankkin.reading.common.Constant
import com.hankkin.reading.control.UserControl
import com.hankkin.reading.ui.user.AuthorizeWebActivity
import com.hankkin.reading.utils.Key4Intent
import com.sunfusheng.glideimageview.GlideImageView
import kotlinx.android.synthetic.main.fragment_person.*

/**
 * Created by huanghaijie on 2018/5/15.
 */
class PersonFragment : BaseMvpFragment<PersonContract.IPresenter>(), PersonContract.IView {


    override fun registerPresenter() = PersonPresenter::class.java

    override fun getLayoutId(): Int {
        return R.layout.fragment_person
    }

    override fun initData() {
        ll_person_avatar.setOnClickListener { llAvatarClick() }
    }

    override fun initView() {
        setUserHeader()
    }

    fun llAvatarClick() {
        if (!UserControl.isLogin()) {
            val authorizeUrl = Constant.OSChinaUrl.BASE_URL +
                    "oauth2/authorize?response_type=code&client_id=${Constant.OSChinaUrl.CLIENT_ID}&redirect_uri=${Constant.OSChinaUrl.REDIRECT_URL}"
            val intent = Intent(activity, AuthorizeWebActivity::class.java)
            intent.putExtra(Key4Intent.KEY_WEB_URL,authorizeUrl )
            intent.putExtra(Key4Intent.KEY_WEB_TITLE, resources.getString(R.string.person_authorize_login))
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        setUserHeader()
    }

    fun setUserHeader(){
        if (UserControl.isLogin()){
            val user = UserControl.getCurrentUser()
            tv_person_name.text = user!!.name
            if (user.avatar.isNotEmpty())
                iv_person_avatar.loadCircleImage(user.avatar,R.mipmap.icon_person_avatar)
        }
        else{
            tv_person_name.text = resources.getString(R.string.person_no_login)
        }
    }

}