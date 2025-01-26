package xmlToJson

import static org.junit.Assert.assertTrue

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.junit.jupiter.api.Test

import yamtl.core.YAMTLModule
import yamtl.groovy.YAMTLGroovyExtensions_dynamicEMF
import yamtl.utils.EMFComparator

class XmlToJsonConverterTest extends YAMTLModule {
    final BASE_PATH = 'model'
    
    @Test
    def void testXmlToJsonConversion() {
        // model transformation execution example
        def xmlInputRes = YAMTLModule.preloadMetamodel(BASE_PATH + '/xmlinput.ecore') as Resource
		println("xml input loaded")
        def jsonOutputRes = YAMTLModule.preloadMetamodel(BASE_PATH + '/jsonoutput.ecore') as Resource
		println("json output loaded")
        def xform = new XmlToJsonConverter(xmlInputRes.contents[0] as EPackage, jsonOutputRes.contents[0] as EPackage)
		println("xform loaded")
        xform.loadInputModels(['xml': BASE_PATH + '/example.xml'])
		println("xml input model loaded")
        xform.execute()
		println("xform executed")
        xform.saveOutputModels(['json': BASE_PATH + '/example.json'])
		println("output model saved")

//        // test assertion
//        def actualModel = xform.getOutputModel('out')
//        EMFComparator comparator = new EMFComparator()
//        def expectedResource = xform.loadModel(BASE_PATH + '/expected.json', false)
//        assertTrue(comparator.equals(expectedResource.getContents(), actualModel.getContents()))
    }
}