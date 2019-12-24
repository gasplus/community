import { axios } from '@/utils/request'
//http://139.129.89.115:9080/jeecg-boot/接口地址
const xiaoQuId = 1
const api = {
  personMonitorList: 'monitor/monitorPersonRecord/list',
  todayStat: 'monitor/monitorPersonRecord/getTodayStat',
  louDongInfo: 'monitor/monitorPerson/getLouDongInfo',
  monitorPersonStat: 'monitor/monitorPerson/getMonitorPersonTypeStat',
  fangJianPerson: 'monitor/monitorPerson/getFangJianPerson'
}

export default api

export function getPersonMonitorList(parameter) {
  return axios({
    url: api.personMonitorList,
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
