import { axios } from '@/utils/request'
//http://139.129.89.115:9080/jeecg-boot/接口地址
const xiaoQuId = 1
const api = {
  personMonitorList: 'monitor/monitorPersonRecord/list',
  carMonitorList: 'monitor/monitorCarRecord/list',
  todayStat: 'monitor/monitorPersonRecord/getTodayStat',
  todayCarStat: 'monitor/monitorCarRecord/getTodayStat',
  louDongInfo: 'monitor/monitorPerson/getLouDongInfo',
  monitorPersonStat: 'monitor/monitorPerson/getMonitorPersonTypeStat',
  fangJianPerson: 'monitor/monitorPerson/getFangJianPerson',
  monitorMessage:'monitor/monitorMessage/list',
  carList: 'monitor/monitorCar/list',
  personList: 'monitor/monitorPerson/list',
  monitorCarStat: 'monitor/monitorDevice/getMonitorCarStat',
  deviceList: 'monitor/monitorDevice/getAll',
  personById: 'monitor/monitorPerson/queryById',
  videoUrlConfig: 'monitor/monitorDevice/getVideoUrlConfig',
  flvUrl: 'monitor/monitorDevice/getLiveFlvUrl'
}

export default api

export function getFLVUrl(parameter) {
  parameter = parameter || {}
  parameter.xiaoQuId = xiaoQuId
  return axios({
    url: api.flvUrl,
    method: 'get',
    params: parameter
  })
}

export function getVideoUrlConfig(parameter) {
  parameter = parameter || {}
  parameter.xiaoQuId = xiaoQuId
  return axios({
    url: api.videoUrlConfig,
    method: 'get',
    params: parameter
  })
}

export function getPersonById(parameter) {
  return axios({
    url: api.personList,
    method: 'get',
    params: parameter
  })
}

export function getPersonList(parameter) {
  return axios({
    url: api.personList,
    method: 'get',
    params: parameter
  })
}

export function getCarList(parameter) {
  return axios({
    url: api.carList,
    method: 'get',
    params: parameter
  })
}

export function getMonitorCarStat(parameter) {
  parameter = parameter || {}
  parameter.xiaoQuId = xiaoQuId
  return axios({
    url: api.monitorCarStat,
    method: 'get',
    params: parameter
  })
}

export function getDeviceList(parameter) {
  parameter = parameter || {}
  parameter.xiaoQuId = xiaoQuId
  return axios({
    url: api.deviceList,
    method: 'get',
    params: parameter
  })
}

export function getPersonMonitorList(parameter) {
  return axios({
    url: api.personMonitorList,
    method: 'get',
    params: parameter
  })
}

export function getCarMonitorList(parameter) {
  return axios({
    url: api.carMonitorList,
    method: 'get',
    params: parameter
  })
}

export function getTodayStat(parameter) {
  return axios({
    url: api.todayStat,
    method: 'get',
    params: parameter
  })
}
export function getTodayCarStat(parameter) {
  return  axios({
    url: api.todayCarStat,
    method: 'get',
    params: parameter
  })
}

export function getLouDongInfo(parameter) {
  parameter = parameter || {}
  parameter.xiaoQuId = xiaoQuId
  return axios({
    url: api.louDongInfo,
    method: 'get',
    params: parameter
  })
}


export function getMonitorPersonTypeStat(parameter) {
  parameter = parameter || {}
  parameter.xiaoQuId = xiaoQuId
  return axios({
    url: api.monitorPersonStat,
    method: 'get',
    params: parameter
  })
}

export function getFangJianPerson(parameter) {
  parameter = parameter || {}
  parameter.xiaoQuId = xiaoQuId
  return axios({
    url: api.fangJianPerson,
    method: 'get',
    params: parameter
  })
}

export function getMonitorMessage(parameter) {
  parameter = parameter || {}
  // parameter.xiaoQuId = xiaoQuId
  return axios({
    url: api.monitorMessage,
    method: 'get',
    params: parameter
  })
}
