/**
 * 
 */
var appModule = angular.module("restMetadataForm", [ 'ui.bootstrap' ]);
// var appModule = angular.module('restMetadataForm', [ 'ui.multiselect' ]);

appModule
		.factory(
				'restMetadataForm',
				function($compile, $http) {

					var UIForm = (function() {

						var inputTypes = [ "checkbox", "color", "currency", "date", "datetime", "datetime-local",
								"email", "file", "hidden", "hour", "image", "month", "number", "password", "radio",
								"range", "search", "text", "textarea", "tel", "time", "url", "week" ];

						var inputTextTypes = [ "date", "datetime", "datetime-local", "email", "number", "password",
								"text", "textarea", "tel", "time", "url", "week" ];

						this.makePut = function(jsonData, rootContext, $scope, elId) {
							var html = this.make(jsonData, rootContext, $scope);
							var element = $(elId);
							element.html(html);
							$compile(element.contents())($scope);
						};

						this.make = function(jsonData, rootContext, $scope) {

							$scope.result = jsonData;
							$scope.result.options = {};
							$scope.result.handlers = {};

							$scope.result.handlers.checkboxHandle = function(list, bindValue, targetList) {

								targetList.splice(0, targetList.length);

								angular.forEach(list, function(item) {
									if (angular.isDefined(item.selected) && item.selected === true) {
										targetList.push(eval("item" + (!!bindValue ? bindValue : "")));
									}
								});
							};

							$scope.result.handlers.selectHandle = function(fieldName) {

								getForOptions(getField(fieldName), true);
							};

							var Resolvers = {

								Resolver : function(name) {

									this.canResolve = function(field) {
										throw "UnsupportedOperationException on method 'canResolve' from " + name;
									};
									this.resolve = function(field, inputEl) {
										throw "UnsupportedOperationException on method 'canResolve' from " + name;
									};
									this.resolveAjax = function(field, inputEl) {
										throw "UnsupportedOperationException on method 'canResolve' from " + name;
									};
								},

								SelectResolver : function() {

									Resolvers.Resolver.apply(this, [ "ResolverSelect" ]);

									this.canResolve = function(field) {

										return 'select' === field.metaData.inputType;
									};

									this.resolveAjax = function(field, inputEl) {

										var newSpan = inputEl.before("<span></span>").prev();

										inputEl.appendTo(newSpan);

										var labelEl = inputEl.before("<label></label>").prev();

										this.resolveInternal(field, inputEl, labelEl);

										recompileEl(field, inputEl);
									};

									this.resolve = function(field, inputEl) {

										var newSpan = inputEl.before("span");

										inputEl.appendTo(newSpan);

										var labelEl = inputEl.before("label");

										this.resolveInternal(field, inputEl, labelEl);
									};

									this.resolveInternal = function(field, inputEl, labelEl) {

										labelEl.attr("class", "control-label").attr("for", inputEl.attr("id")).attr(
												"alt",
												Utils.resolvePlacehoder(field.metaData.alt, $scope.result.target))
												.html(
														Utils.resolvePlacehoder(field.metaData.label,
																$scope.result.target));

										if (!!!field.metaData.select)
											return;

										eval("$scope.result.options." + field.name + " = field.metaData.select.options");

										var isList = "list" === field.metaData.select.type;
										var isMap = "map" == field.metaData.select.type;

										var query = "i.";
										if (isList) {
											if (!!field.metaData.select.provider.bindValue)
												query += field.metaData.select.provider.bindValue + " as i."
														+ field.metaData.select.provider.bindLabel;
											else
												query += field.metaData.select.provider.bindLabel;
										} else if (isMap) {
											if (!!field.metaData.select.provider.bindValue) {
												query += "value."
														+ field.metaData.select.provider.bindValue
														+ " as i."
														+ (!!field.metaData.select.provider.bindLabel ? ("value." + field.metaData.select.provider.bindLabel)
																: "key");
											} else {
												query += "value as i."
														+ (!!field.metaData.select.provider.bindLabel ? ("value." + field.metaData.select.provider.bindLabel)
																: "key");
											}
										} else {
											query += "value as i.key";
										}

										var groupBy = (!!field.metaData.select.provider.groupBy ? " group by i."
												+ field.metaData.select.provider.groupBy : "");
										var orderBy = !!field.metaData.select.provider.oderBy ? (" | orderBy:['i."
												+ field.metaData.select.provider.groupBy + "','i."
												+ field.metaData.select.provider.oderBy + "']") : "";

										query += (groupBy + (" for i in result.options." + field.name) + orderBy);

										inputEl.attr("ng-options", query).attr("class", "form-control");

										if (field.metaData.type === 'list') {
											inputEl.prop("multiple", "multiple");
											inputEl.attr("ng-multiple", "true");
										}
									};
								},

								CheckboxResolver : function() {

									Resolvers.Resolver.apply(this, [ "ResolverCheckbox" ]);

									this.canResolve = function(field) {

										return 'checkbox' === field.metaData.inputType;
									};

									this.resolveAjax = function(field, inputEl) {

										var spanEl = inputEl.before("<span></span>").prev();

										var labelEl = inputEl.before("<label></label>").prev();

										inputEl.appendTo(labelEl);

										this.resolveInternal(field, inputEl, spanEl, labelEl);

										inputEl.parent().html($compile(inputEl.parent().contents())($scope));
									};

									this.resolve = function(field, inputEl) {
										var spanEl = inputEl.before("span");
										var labelEl = inputEl.before("label");
										// var labelEl = spanEl.append("label");

										inputEl.appendTo(labelEl);

										this.resolveInternal(field, inputEl, spanEl, labelEl);
									};

									this.resolveInternal = function(field, inputEl, spanEl, labelEl) {

										var isList = false;
										var isMap = false;

										spanEl.attr("class", "control-label").attr("alt",
												Utils.resolvePlacehoder(field.metaData.alt, $scope.result.target))
												.html(
														Utils.resolvePlacehoder(field.metaData.label,
																$scope.result.target));

										if (!!field.metaData.select) {

											var isList = "list" === field.metaData.select.type;
											var isMap = ("map" == field.metaData.select.type)
													|| field.metaData.select.options.length > 0;

											labelEl.attr("class", field.metaData.inputType + "-inline");

											if (isList || isMap) {

												eval("$scope.result.options." + field.name
														+ " = field.metaData.select.options");

												var bindValue = "";
												var bindLabel = "";

												if (isList) {
													bindLabel = field.metaData.select.provider.bindLabel;
													if (!!field.metaData.select.provider.bindValue)
														bindValue = ("." + field.metaData.select.provider.bindValue);
												} else if (isMap) {
													bindValue = ".value";
													if (!!field.metaData.select.provider.bindValue)
														bindValue = ("." + field.metaData.select.provider.bindValue);

													bindLabel = !!field.metaData.select.provider.bindLabel ? field.metaData.select.provider.bindLabel
															: "key";
												}

												labelEl.attr("ng-repeat", "i in result.options." + field.name);
												labelEl.attr("for", (field.metaData.elId + "_{{i." + bindLabel + "}}"));

												inputEl.attr("ng-model", "i.selected");
												inputEl.attr("id", (field.metaData.elId + "_{{i." + bindLabel + "}}"));

												var fieldName = "result.target." + field.name;
												var query = "result.handlers.checkboxHandle(result.options."
														+ field.name + ", \"" + bindValue + "\", " + fieldName + ")";
												inputEl.attr("ng-change", query);

												inputEl.html("{{i." + bindLabel + "}}");
											}
										}

										if (!isList && !isMap) {
											labelEl.attr("class", field.metaData.inputType).attr("alt",
													Utils.resolvePlacehoder(field.metaData.alt, $scope.result.target))
													.html(
															Utils.resolvePlacehoder(field.metaData.label,
																	$scope.result.target));
										}
									};
								},

								RadioResolver : function() {

									Resolvers.CheckboxResolver.apply(this, [ "ResolverRadio" ]);

									this.canResolve = function(field) {

										return 'radio' === field.metaData.inputType;
									};
								},

								GenericResolver : function() {

									Resolvers.Resolver.apply(this, [ "GenericResolver" ]);

									this.canResolve = function(field) {

										return 'input' === field.metaData.elType;
									};

									this.resolveAjax = function(field, inputEl) {

										if (!!field.metaData.help) {
											var help = "<p class='help-block'>"
													+ Utils
															.resolvePlacehoder(field.metaData.help,
																	$scope.result.target) + "</p>";
											inputEl.parent.after(help);
										}

										var labelEl = inputEl.before("<label></label>").prev();

										this.resolveInternal(field, inputEl, labelEl);
									};

									this.resolve = function(field, inputEl) {

										if (!!field.metaData.help) {
											var help = Utils.resolvePlacehoder(field.metaData.help,
													$scope.result.target);
											inputEl.parent().after("p").html(help);
										}

										var labelEl = inputEl.before("label");

										this.resolveInternal(field, inputEl, labelEl);
										// TODO: set validations config
									};

									this.resolveInternal = function(field, inputEl, labelEl) {

										labelEl.attr("class", "input-group-addon").attr("alt",
												Utils.resolvePlacehoder(field.metaData.alt, $scope.result.target))
												.html(
														Utils.resolvePlacehoder(field.metaData.label,
																$scope.result.target));

										if (!!field.metaData.placeholder)
											inputEl.attr("placeholder", field.metaData.placeholder);

										if (!!field.metaData.mask)
											inputEl.attr("mask", field.metaData.mask);

										var elClass = "";
										if (Utils.contains(inputTextTypes, field.metaData.inputType)
												|| 'select' === field.metaData.inputType)
											elClass += "form-control";

										inputEl.attr("class", elClass);

										var readonly = Utils.resolvePlacehoder(field.metaData.readonly,
												$scope.result.target);
										if ("true" === readonly && Utils.contains(inputTextTypes)) {

											inputEl.elType = "p";
											inputEl.attr("class", "form-control-static").html(field.value);
										}
										// TODO: set validations config
									};
								}
							};

							var recompileEl = function(field, inputEl) {

								var tempId = field.name + "_" + new Date().getTime();

								inputEl.before(Utils.createElement("div").attr("id", tempId).html());

								var newEl = inputEl.clone();
								$(inputEl).remove();

								$("#" + tempId).html($compile(newEl)($scope));
								newEl.insertBefore($("#" + tempId));

								$("#" + tempId).remove();
							};

							var getForOptions = function(field, preventWriters) {

								var optionsURL = field.metaData.select.optionsURL.replace("URL:", "");
								var url = Utils.resolvePlacehoder(optionsURL, $scope.result.target);
								url = url.indexOf("http") > -1 ? url : rootContext + url;

								$http.get(url).success(function(data) {

									if (!!data.options) {
										field.metaData.select.options = data.options;
										field.metaData.select.type = data.type;
									} else {

										if (!!data.length) {
											if (!!data[0].key && !!data[0].value)
												field.metaData.select.type = 'map';
											else
												field.metaData.select.type = 'list';
										} else {
											if (!!data.key && !!data.value)
												field.metaData.select.type = 'map';
											else {
												var keys = Object.keys(data);
												var map = new Array();
												for (var i = 0; i < keys.length; i++) {
													map.push(Utils.entry(keys[i], data[keys[i]]));
												}
												data = map;
												field.metaData.select.type = 'map';
											}
										}
										field.metaData.select.options = data;
									}
									eval("$scope.result.options." + field.name + " = field.metaData.select.options");

									if (!!preventWriters)
										return;

									var inputEl = $("#" + field.metaData.elId);

									var placeholders = Utils.getPlacehoders(optionsURL);
									var elIds = new Array();
									for (var i = 0; i < placeholders.length; i++) {
										var idx = placeholders[i].indexOf(".");
										if (idx > -1)
											placeholders[i] = placeholders[i].substr(0, idx);

										elIds.push(fieldElIdsMap.get(placeholders[i]));
									}
									callForResolver(field, inputEl, true, elIds);

								}).error(function(data, status) {
									throw (status);
								});
							};

							var callForResolver = function(field, inputEl, ajax, elIds) {

								for (var i = 0; i < resolvers.length; i++) {
									if (resolvers[i].canResolve(field, inputEl)) {

										if (!!ajax)
											resolvers[i].resolveAjax(field, inputEl);
										else
											resolvers[i].resolve(field, inputEl);

										if (!!elIds) {

											for (var j = 0; j < elIds.length; j++) {

												var el = $("#" + elIds[j].value);
												var _field = getField(elIds[j].key);

												if (!!_field) {

													var event = el.attr("ng-change");
													event = !!event ? event : "";
													var newEvent = "result.handlers.selectHandle('" + field.name
															+ "'); ";
													if (event.indexOf(newEvent) == -1) {

														event += newEvent;
														el.attr("ng-change", event);
														recompileEl(_field, el);
													}
												}
											}
										}
										return;
									}

								}
							};

							var getField = function(fieldName) {

								for (var g_i = 0; g_i < $scope.result.groupFields.length; g_i++) {

									for (var f_i = 0; f_i < $scope.result.groupFields[g_i].value.length; f_i++) {

										var field = $scope.result.groupFields[g_i].value[f_i];
										if (field.name === fieldName)
											return field;
									}
								}
								return undefined;
							};

							var resolvers = new Array();
							resolvers.push(new Resolvers.SelectResolver());
							resolvers.push(new Resolvers.CheckboxResolver());
							resolvers.push(new Resolvers.RadioResolver());
							resolvers.push(new Resolvers.GenericResolver());

							var fieldElIdsMap = Utils.map();

							var ajaxOptions = new Array();

							var rootDiv = Utils.createElement("div").attr("class", "metadata-form form-horizontal");

							rootDiv.attr("role", "form");

							for (var g_i = 0; g_i < $scope.result.groupFields.length; g_i++) {

								var groupField = $scope.result.groupFields[g_i];
								var group = $scope.result.groups[groupField.key];
								// Utils.mergeAttr(group.attrs,
								// Utils.entry("class", "panel panel-default
								// animate-show")));
								group.attrs.push(Utils.entry("class", "panel panel-default animate-show"));
								group.attrs = Utils.resolvePlacehoder(group.attrs, "result.target.");

								var groupDiv = rootDiv.append("div", group.attrs);
								groupDiv.append("div", Utils.entry("class", "panel-heading")).append("h3",
										Utils.entry("class", "panel-title")).html(
										Utils.resolvePlacehoder(group.label, $scope.result.target));

								groupDiv = groupDiv.append("div", Utils.entry("class", "panel-body"));

								for (var f_i = 0; f_i < groupField.value.length; f_i++) {

									var field = groupField.value[f_i];

									var groupFields = groupDiv.append("div").attr("class",
											"input-group input-group-" + field.metaData.inputType);

									field.metaData.elType = field.metaData.inputType;

									if (Utils.contains(inputTypes, field.metaData.elType)) {
										field.metaData.attrs.push(Utils.entry("type", field.metaData.inputType));
										field.metaData.elType = "input";
									}
									field.metaData.attrs = Utils.resolvePlacehoder(field.metaData.attrs,
											"result.target.");

									var inputEl = groupFields.append(field.metaData.elType, field.metaData.attrs);
									inputEl.attr("ng-model", "result.target." + field.name);

									field.metaData.elId = field.metaData.inputType + "_" + field.name;
									inputEl.attr("id", field.metaData.elId);

									fieldElIdsMap.put(field.name, field.metaData.elId);

									if (!!field.metaData.select) {
										if (typeof field.metaData.select.options === "string") {

											field.metaData.select.optionsURL = field.metaData.select.options;
											ajaxOptions.push(field);
										} else {

											callForResolver(field, inputEl);
										}
									} else {

										callForResolver(field, inputEl);
									}
								}
							}

							setTimeout(function() {
								for (var i = 0; i < ajaxOptions.length; i++) {
									getForOptions(ajaxOptions[i]);
								}
							}, 50);

							return rootDiv.html();
						};

						var Utils = {

							contains : function(array, item) {
								for (var i = 0; i < array.length; i++) {
									if (array[i] === item)
										return true;
								}
								return false;
							},

							resolvePlacehoder : function(value, target) {
								value = Utils.translateIfN(value);

								var v = Utils.checkEntry(value);
								try {
									var er = /\$\{[^\}]*\}/g

									if (v instanceof Array) {
										for (var i = 0; i < v.length; i++) {

											var entryV = Utils.checkEntry(v[i]);
											var entryV = Utils.decode(entryV);

											var vReplace = entryV.replace(er, function(exp, n1, n2) {
												var r = exp.replace(/[\}\{\$]/g, "");
												return (typeof target === "string") ? (target + r)
														: eval("target." + r);
											});

											v[i] = Utils.checkEntryValue(v[i], vReplace);
										}
										return Utils.checkEntryValue(value, v);
									}

									var entryV = Utils.checkEntry(v);
									var entryV = Utils.decode(entryV);
									var vReplace = entryV.replace(er, function(exp, n1, n2) {
										var r = exp.replace(/[\}\{\$]/g, "");
										return (typeof target === "string") ? (target + r) : eval("target." + r);
									});

									v = Utils.checkEntryValue(v, vReplace);
								} catch (e) {
								}

								return Utils.checkEntryValue(value, v);
							},

							getPlacehoders : function(value) {
								value = Utils.translateIfN(value);

								var v = Utils.checkEntry(value);
								var placeholders = new Array();
								try {
									var er = /\$\{[^\}]*\}/g

									while ((result = er.exec(v))) {
										placeholders.push(result[0].replace(/[\}\{\$]/g, ""));
									}

								} catch (e) {
								}

								return placeholders;
							},

							translateIfN : function(value) {

								var v = Utils.checkEntry(value);
								try {
									var er = /\#\[[^\]]*\]/g;

									if (v instanceof Array) {
										for (var i = 0; i < v.length; i++) {

											var entryV = Utils.checkEntry(v[i]);
											var entryV = Utils.decode(entryV);

											var vReplace = entryV.replace(er, function(exp, n1, n2) {
												return Utils.translate(exp.replace(/[\]\[\#]/g, ""));
											});

											v[i] = Utils.checkEntryValue(v[i], vReplace);
										}
										return Utils.checkEntryValue(value, v);
									}

									var entryV = Utils.checkEntry(v);
									var entryV = Utils.decode(entryV);
									var vReplace = entryV.replace(er, function(exp, n1, n2) {
										return Utils.translate(exp.replace(/[\]\[\#]/g, ""));
									});

									v = Utils.checkEntryValue(v, vReplace);
								} catch (e) {
								}
								return Utils.checkEntryValue(value, v);
							},

							translate : function(value) {
								// TODO i18n
								return "[" + value + "]";
							},

							decode : function(value) {

								return decodeURIComponent(decodeURIComponent(value));
							},

							checkEntry : function(entry) {

								return !!entry.value ? entry.value : entry;
							},

							checkEntryValue : function(entry, value) {
								if (!!entry.value)
									entry.value = value;
								else
									entry = value;

								return entry;
							},

							entry : function(_key, _value) {
								return {
									key : _key,
									value : _value
								};
							},

							map : function() {

								var Map = (function() {

									this.itens = new Array();

									this.put = function(_key, _value) {
										this.itens.push(Utils.entry(_key, _value));
									};

									this.get = function(_key) {
										for (var i = 0; i < this.itens.length; i++) {
											if (this.itens[i].key === _key) {
												return this.itens[i];
											}
										}
									};

									this.getValue = function(_key) {
										return this.get(_key).value;
									};
								});

								return new Map();
							},

							createElement : function(_elType, _attrs) {

								var El = (function(_elType, _attrs) {

									this.elType = _elType;
									this._html = "";
									this.childs = new Array();
									this.index = 0;
									this.parentNode = this;
									this.attrs = new Array();
									if (!!_attrs) {
										for (var i = 0; i < _attrs.length; i++) {
											this.attrs.push(Utils.entry(!!_attrs[i].name ? _attrs[i].name
													: _attrs[i].key, _attrs[i].value));
										}
									}

									this.before = function(el, _attrs) {
										if (typeof el === "string") {
											el = this.parentNode.append(el, _attrs);
										}
										this.moveIndexTo(el, this.index)
										return el;
									};

									this.after = function(el, _attrs) {
										if (typeof el === "string") {
											el = this.parentNode.append(el, _attrs);
										}
										return this.moveIndexTo(el, _attrs, this.index + 1);
									};

									this.appendTo = function(newEl) {

										this.remove();
										newEl.append(this);

										return this;
									};

									this.moveIndexTo = function(newEl, _index) {
										for (var i = 0; i < this.parentNode.childs.length; i++) {
											if (this.parentNode.childs[i].index >= _index) {
												this.parentNode.childs[i].index++;
											}
										}
										newEl.index = _index;
										newEl.parentNode = this.parentNode;
										return newEl;
									};

									this.append = function(nEl, _attrs) {
										if (!!_attrs && !(_attrs instanceof Array)) {
											var nAttrs = new Array();
											nAttrs.push(_attrs);
											_attrs = nAttrs;
										}
										if (typeof nEl === "string")
											nEl = Utils.createElement(nEl, _attrs);

										nEl.index = this.childs.length + 1;
										nEl.parentNode = this;
										this.childs.push(nEl);

										return nEl;
									};

									this.insertBefore = function(elId) {
										elId = elId.replace("#", "");
										var newEl;
										for (var i = 0; i < this.parentNode.childs.length; i++) {
											for (var a = 0; a < this.parentNode.childs[i].attrs.length; a++) {
												if ("id" === this.parentNode.childs[i].attrs[a].key
														&& elId === this.parentNode.childs[i].attrs[a].value) {
													newEl = this.parentNode.childs[i];
													break;
												}
											}
										}
										if (!!newEl) {
											return newEl.before(this);
										}
										return this;
									}

									this.clone = function() {

										var nEl = Utils.createElement(this.elType, this.attrs);

										nEl._html = this._html;
										nEl.childs = this.childs;
										nEl.index = this.index;
										nEl.parentNode = this.parentNode;

										return nEl;
									};

									this.remove = function(index) {

										var newChilds = new Array();

										for (var i = 0; i < this.parentNode.childs.length; i++) {
											if (this.parentNode.childs[i].index != index) {
												this.parentNode.childs[i].index = i;
												newChilds.push(this.parentNode.childs[i]);
											}
										}
										this.parentNode.childs = newChilds;

										return this;
									};

									this.remove = function() {

										var newChilds = new Array();

										for (var i = 0; i < this.parentNode.childs.length; i++) {
											if (this.parentNode.childs[i].index != this.index) {
												this.parentNode.childs[i].index = i;
												newChilds.push(this.parentNode.childs[i]);
											}
										}
										this.parentNode.childs = newChilds;

										return this.parentNode;
									};

									this.attr = function(name, value) {
										for (var i = 0; i < this.attrs.length; i++) {
											if (this.attrs[i].key === name) {
												if (!!value) {
													this.attrs[i].value = value;
													return this;
												} else {
													return this.attrs[i].value;
												}
											}
										}
										if (!!value)
											this.attrs.push(Utils.entry(name, value));

										return this;
									};

									this.prev = function() {

										if (this.index > 0)
											return this.parentNode.childs[this.index - 1];

										return this.parentNode;
									};

									this.next = function() {

										if (this.parentNode.childs.length > this.index)
											return this.parentNode.childs[this.index + 1];

										return this.parentNode.next();
									};

									this.prop = function(name) {
										this.attr(name, undefined);
									};

									this.html = function(content) {

										if (!!content) {
											this._html = content;
											return this;
										}

										var html = "<" + this.elType + " ";

										for (var i = 0; i < this.attrs.length; i++) {
											html += this.attrs[i].key
													+ (!!this.attrs[i].value ? ("='" + this.attrs[i].value + "' ")
															: " ");
										}
										html += ">" + this._html;

										var sortChilds = this.childs.sort(function(a, b) {
											return a.index - b.index;
										});

										for (var i = 0; i < sortChilds.length; i++) {
											html += sortChilds[i].html();
										}
										html += "</" + this.elType + ">";

										return html;
									};
								});

								return new El(_elType, _attrs);
							}
						};

						return this;
					});

					return new UIForm();
				});