
import { filterObj } from '@/utils/util';
import { deleteAction, getAction,downFile } from '@/api/manage'
import Vue from 'vue'
import { ACCESS_TOKEN } from "@/store/mutation-types"

export const deviceMixin = {
  data(){
    return {
      deviceDetailShow: false
    }
  },
  created() {
  },
  methods:{
    closeDeviceDetail() {
      this.deviceDetailShow = false
    },
    showDeviceVideo(deviceId) {
      this.deviceDetailShow = true
      this.$nextTick(() => {
        this.$refs.deviceDetail.showVideo(deviceId)
      })
    }
  }

}