<?php

namespace MirrorApiBundle\Controller;

use MirrorApiBundle\Entity\Time;
use MirrorApiBundle\Entity\Weather;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;

class UserController extends Controller
{
    /**
     * @Route(  "/{user_id}",
     *     requirements={"user_id" = "\d+"},
     *     defaults={"user_id" = 0},
     *     name="get_user"))
     * @return Response
     */
    public function getuserAction(Request $request)
    {
        $repository = $this->getDoctrine()->getRepository('MirrorApiBundle:User');
        try {
            $user = $repository->getUserAndModules($request->get("user_id"));
        } catch(\Exception $exception) {
            return new JsonResponse(["error" => "This user don't exist"], Response::HTTP_NOT_FOUND);
        }

        $retour = [
            "first_name"    => $user->getFirstName(),
            "last_name"     => $user->getLastName(),
            "href"          => $this->generateUrl('get_user',[], UrlGeneratorInterface::ABSOLUTE_URL)
        ];

        foreach ($user->getModules() as $module) {
            if (is_a($module, Weather::class)) {
                $retour["weather"][] = [
                    "id"        => $module->getId(),
                    "position"  => $module->getPosition(),
                    "name"      => $module->getName(),
                    "city"      => $module->getCity(),
                    "country"   => $module->getCountry(),
                    "href"      => "",
                ];
            }
            if (is_a($module, Time::class)) {
                $retour["time"][] = [
                    "id"        => $module->getId(),
                    "position"  => $module->getPosition(),
                    "name"      => $module->getName(),
                    "timezone"  => $module->getTimeZone(),
                    "href"      => "",
                ];
            }
        }
        
        return new JsonResponse($retour);
        //return $this->render('MirrorApiBundle:User:index.html.twig', []);
    }
}
