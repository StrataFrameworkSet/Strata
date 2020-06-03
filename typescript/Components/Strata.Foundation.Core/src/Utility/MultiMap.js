"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.MultiMap = void 0;
class MultiMap {
    constructor() {
        this.imp = new Map();
    }
    put(key, value) {
        if (key == null)
            throw new Error("key is null");
        if (!this.imp.has(key))
            this.imp.set(key, new Array());
        this.imp.get(key).push(value);
        return this;
    }
    remove(key, value) {
        if (value == undefined) {
            if (this.imp.has(key))
                this.imp.delete(key);
        }
        else {
            if (this.imp.has(key)) {
                let values = this.imp.get(key);
                values.slice(values.indexOf(value), 1);
            }
        }
        return this;
    }
    get(key, index) {
        if (index == undefined) {
            return this.imp.get(key);
        }
        else {
            return;
            this
                .imp
                .get(key)
                .find((v, i, o) => i == index);
        }
    }
    getCardinality(key) {
        return this.imp.get(key).length;
    }
    getKeys() {
        return Array.from(this.imp.keys());
    }
    getValues() {
        return Array.from(this.imp.values());
    }
    containsKey(key) {
        return this.imp.has(key);
    }
    containsValue(key, value) {
        if (this.containsKey(key))
            return this.get(key).includes(value);
        return false;
    }
}
exports.MultiMap = MultiMap;
//# sourceMappingURL=MultiMap.js.map