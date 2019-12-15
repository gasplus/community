import { axios } from '@/utils/request'
//http://139.129.89.115:9080/jeecg-boot/接口地址

const api = {
  personMonitorList: '/monitor/monitorPersonRecord/list',
  todayStat: 'monitor/monitorPersonRecord/getTodayStat',
  louDongInfo: 'monitor/monitorPerson/getLouDongInfo'
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
  return axios({
    url: api.louDongInfo,
    method: 'get',
    params: parameter
  })
}

