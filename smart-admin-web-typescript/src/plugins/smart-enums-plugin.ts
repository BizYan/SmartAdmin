/*
 * 枚举插件
 * 此插件为 1024创新实验室 自创的插件
 *
 * @Author:    1024创新实验室-主任：卓大
 * @Date:      2022-09-06 20:51:03
 * @Wechat:    zhuda1024
 * @Email:     lab1024@163.com
 * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
 */
import _ from 'lodash';
import { App } from 'vue';
import { FLAG_NUMBER_ENUM } from '/@/constants/common-const';
import { SmartEnum, SmartEnumWrapper, SmartEnumItem, SmartEnumPlugin } from '/@/types/smart-enum';

export default {
  install: <T>(app: App, smartEnumWrapper: SmartEnumWrapper<T>): void => {
    const smartEnumPlugin = {} as SmartEnumPlugin;
    /**
     * 根据枚举值获取描述
     * @param {*} constantName 枚举名
     * @param {*} value          枚举值
     * @returns
     */
    smartEnumPlugin.getDescByValue = function (constantName: string, value: any): string {
      if (!smartEnumWrapper || !Object.prototype.hasOwnProperty.call(smartEnumWrapper, constantName)) {
        console.error('无法找到变量名称：' + constantName + '，请检查 /constants/index.js 文件中是否引入此变量！');
        return '';
      }
      // boolean类型需要做特殊处理
      if (constantName === 'FLAG_NUMBER_ENUM' && !_.isUndefined(value) && typeof value === 'boolean') {
        value = value ? FLAG_NUMBER_ENUM.TRUE.value : FLAG_NUMBER_ENUM.FALSE.value;
      }

      let smartEnum: SmartEnum<T> = smartEnumWrapper[constantName];
      for (let item in smartEnum) {
        if (smartEnum[item].value === value) {
          return smartEnum[item].desc;
        }
      }
      return '';
    };
    /**
     * 根据枚举名获取对应的描述键值对[{value:desc}]
     * @param {*} constantName 枚举名
     * @returns
     */
    smartEnumPlugin.getValueDescList = function <T>(constantName: string): SmartEnumItem<T>[] {
      if (!Object.prototype.hasOwnProperty.call(smartEnumWrapper, constantName)) {
        console.error('无法找到变量名称：' + constantName + '，请检查 /constants/index.js 文件中是否引入此变量！');
        return [];
      }
      const result: SmartEnumItem<T>[] = [];
      let targetSmartEnum = smartEnumWrapper[constantName];
      for (let item in targetSmartEnum) {
        result.push(targetSmartEnum[item] as any);
      }
      return result;
    };

    /**
     * 根据枚举名获取对应的value描述键值对{value:desc}
     * @param {*} constantName 枚举名
     * @returns
     */
    smartEnumPlugin.getValueDesc = function (constantName: string): { [key: string]: string } {
      if (!Object.prototype.hasOwnProperty.call(smartEnumWrapper, constantName)) {
        console.error('无法找到变量名称：' + constantName + '，请检查 /constants/index.js 文件中是否引入此变量！');
        return {};
      }
      let smartEnum = smartEnumWrapper[constantName];
      let result = {} as { [key: string]: string };
      for (let item in smartEnum) {
        let key: string = smartEnum[item].value + '';
        result[key] = smartEnum[item].desc;
      }
      return result;
    };

    app.config.globalProperties.$smartEnumPlugin = smartEnumPlugin;
    app.provide('smartEnumPlugin', smartEnumPlugin);
  },
};
