package xmlToJson

import static yamtl.dsl.Rule.*

import org.eclipse.emf.ecore.EPackage

import yamtl.core.YAMTLModuleGroovy
import groovy.json.JsonBuilder
import groovy.xml.XmlSlurper


class XmlToJsonConverter extends YAMTLModuleGroovy {

    public XmlToJsonConverter(EPackage inPk, EPackage outPk) {
		println("executed 1")
        header().in('xml', inPk).out('json', outPk)
		println("executed 2")
        ruleStore([
            rule('xml2json')
                .in('row')
                .filter { it.name() == 'root' }
                .query()
                .endWith({
                    def jsonBuilder = new JsonBuilder()
                    jsonBuilder {
                        convertToJson(root, delegate)
                    }
                    println jsonBuilder.toPrettyString()
                })
        ])
    }

    def convertToJson(node, json) {
        if (node instanceof String) {
            return node
        }

        node.children().each { child ->
            def childName = child.name()
            def childValue = child.children().size() > 0 ? convertToJson(child, [:]) : child.text()

            if (json.containsKey(childName)) {
                if (!(json[childName] instanceof List)) {
                    json[childName] = [json[childName]]
                }
                json[childName] << childValue
            } else {
                json[childName] = childValue
            }
        }

        node.attributes().each { attr, value ->
            json["@\${attr}"] = value
        }

        return json
    }
}